package org.example.project.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

class AuthRepositoryImpl : AuthRepository {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override suspend fun signInWithEmail(email: String, password: String) = runCatching {
        auth.signInWithEmailAndPassword(email, password)
        Unit
    }

    override suspend fun signUp(email: String, password: String) = runCatching {
        val authResult = auth.createUserWithEmailAndPassword(email, password)
        val user = authResult.user
        if (user != null) {
            // iOS specific time fetcher
            val currentTime = (NSDate().timeIntervalSince1970 * 1000).toLong()
            val userData = mapOf(
                "uid" to user.uid,
                "email" to email,
                "createdAt" to currentTime
            )
            db.collection("users").document(user.uid).set(userData)
        }
        Unit
    }

    override suspend fun signInWithGoogle(): Result<Unit> {
        return Result.failure(Exception("LAUNCH_GOOGLE_SIGN_IN_IOS"))
    }

    override suspend fun firebaseAuthWithGoogle(idToken: String) = runCatching {
        // Note: GitLive iOS requires an accessToken string parameter, even if empty.
        val credential = GoogleAuthProvider.credential(idToken, accessToken = "")
        val authResult = auth.signInWithCredential(credential)
        val user = authResult.user

        if (user != null) {
            val currentTime = (NSDate().timeIntervalSince1970 * 1000).toLong()
            val userData = mapOf(
                "uid" to user.uid,
                "email" to (user.email ?: ""),
                "displayName" to (user.displayName ?: ""),
                "lastLogin" to currentTime
            )
            db.collection("users").document(user.uid).set(userData)
        }
        Unit
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}