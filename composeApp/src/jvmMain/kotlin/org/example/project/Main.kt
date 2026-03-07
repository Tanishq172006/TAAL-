package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {

    val audioPlayer by lazy { AudioPlayer() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "TAAL"
    ) {
        App(audioPlayer)
    }
}