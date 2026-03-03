package org.example.project

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.LineEvent
import java.util.concurrent.CopyOnWriteArrayList

actual class AudioPlayer {
    private val activeClips = CopyOnWriteArrayList<Clip>()

    actual fun play(resourceName: String) {
        val fileName = if (resourceName.endsWith(".wav")) resourceName else "$resourceName.wav"

        try {
            val inputStream = javaClass.classLoader.getResourceAsStream(fileName.lowercase()) ?: return
            val audioStream: AudioInputStream = AudioSystem.getAudioInputStream(inputStream)
            val clip = AudioSystem.getClip()
            activeClips.add(clip)
            
            clip.addLineListener { event ->
                if (event.type == LineEvent.Type.STOP) {
                    clip.close()
                    activeClips.remove(clip)
                }
            }
            
            clip.open(audioStream)
            clip.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun pause() {
        activeClips.forEach { it.stop() }
    }

    actual fun stop() {
        activeClips.forEach {
            it.stop()
            it.close()
        }
        activeClips.clear()
    }

    actual fun release() {
        stop()
    }

    actual val isPlaying: Boolean
        get() = activeClips.any { it.isRunning }
}

actual fun createAudioPlayer(): AudioPlayer {
    return AudioPlayer()
}
