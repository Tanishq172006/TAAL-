package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts // Added for Launcher
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope // Added for lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn // Added for GoogleSignIn
import com.google.android.gms.common.api.ApiException // Added for ApiException
import kotlinx.coroutines.launch
import org.example.project.auth.AuthRepositoryImpl

class MainActivity : ComponentActivity() {

    lateinit var audioImporter: AudioImporter
    lateinit var audioPlayer: AudioPlayer
    private lateinit var authRepo: AuthRepositoryImpl


    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                // Use lifecycleScope to call the suspend function
                lifecycleScope.launch {
                    authRepo.firebaseAuthWithGoogle(token)
                }
            }
        } catch (e: ApiException) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppContextHolder.context = applicationContext
        AudioImporter.currentActivity = this

        audioPlayer = AudioPlayer(this)
        authRepo = AuthRepositoryImpl(this)
        val audioPlayer = AudioPlayer(this)

        setContent {

            App(
                audioPlayer = audioPlayer,
                authRepository = authRepo,
                onGoogleSignInClick = {
                    val intent = authRepo.googleSignInClient.signInIntent
                    googleSignInLauncher.launch(intent)
                }
            )
        }
    }
}