package org.example.project

actual class AudioPlayer {
    actual fun play(resourceName: String) {
        // JS stub
    }

    actual fun pause() {
        // JS stub
    }

    actual fun stop() {
        // JS stub
    }

    actual fun release() {
        // JS stub
    }

    actual val isPlaying: Boolean
        get() = false
}

actual fun createAudioPlayer(): AudioPlayer {
    return AudioPlayer()
}
