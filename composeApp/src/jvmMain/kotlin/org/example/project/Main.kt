package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.auth.AuthRepository

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "TAAL"
    ) {

        val audioPlayer = remember { AudioPlayer() }

        val fakeAuthRepository = remember {
            object : AuthRepository {

                override suspend fun signInWithEmail(email: String, password: String) =
                    Result.success(Unit)

                override suspend fun signUp(email: String, password: String) =
                    Result.success(Unit)

                override suspend fun signInWithGoogle() =
                    Result.success(Unit)

                override suspend fun firebaseAuthWithGoogle(idToken: String) =
                    Result.success(Unit)

                override fun signOut() {}

                override fun getCurrentUserId(): String? = "desktop_user"
            }
        }

        App(
            audioPlayer = audioPlayer,
            authRepository = fakeAuthRepository,
            onGoogleSignInClick = {}
        )
    }
}