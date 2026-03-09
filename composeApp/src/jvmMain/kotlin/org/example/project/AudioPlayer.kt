package org.example.project

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

actual class AudioPlayer {

    private val clips = mutableMapOf<String, Clip>()

    actual fun playSound(name: String) {
        playSample(name)
    }

    private fun playSample(name: String) {

        try {

            val clip = clips.getOrPut(name) {

                val resource = Thread.currentThread()
                    .contextClassLoader
                    ?.getResource(name)
                    ?: throw IllegalArgumentException("Sound resource not found: $name")

                val stream = AudioSystem.getAudioInputStream(resource)

                val c = AudioSystem.getClip()
                c.open(stream)
                c
            }

            if (clip.isRunning) {
                clip.stop()
            }

            clip.framePosition = 0
            clip.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}