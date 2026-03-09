package org.example.project.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.example.project.auth.AuthRepository

class AuthRepositoryImpl(private val context: Context) : AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val googleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("464898087369-kga1alvnsrma2t3n9cadhj38rvcqv928.apps.googleusercontent.com")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
        Unit
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> = runCatching {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val user = authResult.user

        if (user != null) {
            val userData = mapOf(
                "uid" to user.uid,
                "email" to email,
                "createdAt" to System.currentTimeMillis()
            )
            db.collection("users").document(user.uid).set(userData).await()
        }
        Unit
    }

    override suspend fun signInWithGoogle(): Result<Unit> {
        return Result.failure(Exception("LAUNCH_GOOGLE_INTENT"))
    }

    override suspend fun firebaseAuthWithGoogle(idToken: String): Result<Unit> = runCatching {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(credential).await()
        val user = authResult.user

        if (user != null) {
            val userData = mapOf(
                "uid" to user.uid,
                "email" to (user.email ?: ""),
                "displayName" to (user.displayName ?: ""),
                "lastLogin" to System.currentTimeMillis()
            )
            db.collection("users").document(user.uid).set(userData).await()
        }
        Unit
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}