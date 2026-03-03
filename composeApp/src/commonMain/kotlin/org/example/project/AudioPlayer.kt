package org.example.project

expect class AudioPlayer {
    fun play(resourceName: String)
    fun pause()
    fun stop()
    fun release()
    val isPlaying: Boolean
}

expect fun createAudioPlayer(): AudioPlayer
