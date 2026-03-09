package org.example.project.auth

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signInWithGoogle(): Result<Unit>
    suspend fun firebaseAuthWithGoogle(idToken: String): Result<Unit>
    fun signOut()
    fun getCurrentUserId(): String?
}