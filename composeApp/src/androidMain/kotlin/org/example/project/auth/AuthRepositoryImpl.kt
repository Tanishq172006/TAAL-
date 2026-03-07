package org.example.project.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
// 1. ADD THIS IMPORT
import org.example.project.auth.AuthRepository

class AuthRepositoryImpl(private val context: Context) : AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Configure Google Sign-In
    val googleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_CLIENT_ID")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
        Unit
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> = runCatching {
        auth.createUserWithEmailAndPassword(email, password).await()
        Unit
    }

    override suspend fun signInWithGoogle(): Result<Unit> {
        // This is a placeholder for the UI to trigger the Intent
        return Result.failure(Exception("LAUNCH_GOOGLE_INTENT"))
    }

    // 2. This now matches the updated interface
    override suspend fun firebaseAuthWithGoogle(idToken: String): Result<Unit> = runCatching {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).await()
        Unit
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}