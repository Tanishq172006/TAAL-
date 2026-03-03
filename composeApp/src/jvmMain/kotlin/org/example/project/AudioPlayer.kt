package org.example.project

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

actual class AudioPlayer actual constructor() {

    actual fun playSound(name: String) {
        val fileName = when (name) {
            "drum" -> "drum.wav"
            "guitar" -> "guitar.wav"
            "sax" -> "sax.wav"
            else -> return
        }

        val audioInputStream =
            javaClass.classLoader.getResource(fileName)?.let {
                AudioSystem.getAudioInputStream(it)
            } ?: return

        val clip: Clip = AudioSystem.getClip()
        clip.open(audioInputStream)
        clip.start()
    }
}