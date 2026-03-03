package org.example.project

import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.AVFoundation.*
import platform.Foundation.*
import platform.darwin.NSObject
import kotlinx.cinterop.ExperimentalForeignApi

actual class AudioPlayer {
    private val activePlayers = mutableListOf<AVAudioPlayer>()
    private val delegate = PlayerDelegate { player ->
        activePlayers.remove(player)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun play(resourceName: String) {
        val name = resourceName.removeSuffix(".wav").lowercase()
        val fileName = when (name) {
            "drum" -> "drum"
            "guitar" -> "guitar"
            "sax" -> "sax"
            else -> name
        }

        val path = NSBundle.mainBundle.pathForResource(fileName, "wav") ?: return
        val url = NSURL.fileURLWithPath(path)

        try {
            val player = AVAudioPlayer(contentsOfURL = url, error = null)
            player.delegate = delegate
            activePlayers.add(player)
            player.play()
        } catch (e: Exception) {
            // Handle error
        }
    }

    actual fun pause() {
        activePlayers.forEach { it.pause() }
    }

    actual fun stop() {
        activePlayers.forEach { it.stop() }
        activePlayers.clear()
    }

    actual fun release() {
        stop()
    }

    actual val isPlaying: Boolean
        get() = activePlayers.any { it.isPlaying() }

    private class PlayerDelegate(val onFinished: (AVAudioPlayer) -> Unit) : NSObject(), AVAudioPlayerDelegateProtocol {
        override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
            onFinished(player)
        }
    }
}

actual fun createAudioPlayer(): AudioPlayer {
    return AudioPlayer()
}
