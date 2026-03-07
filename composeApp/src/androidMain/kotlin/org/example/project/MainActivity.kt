package org.example.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    lateinit var audioImporter: AudioImporter
    lateinit var audioPlayer: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        AppContextHolder.context = applicationContext
        AudioImporter.currentActivity = this

        audioPlayer = AudioPlayer(this)

        setContent {
            App(audioPlayer)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        AudioImporter().onActivityResult(requestCode, data)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // preview cannot access Android context, so this should not create AudioPlayer
}