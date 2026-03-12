package org.example.project

import javax.sound.sampled.*

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

                val originalStream = AudioSystem.getAudioInputStream(resource)

                val baseFormat = originalStream.format

                val decodedFormat = AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.sampleRate,
                    16,
                    baseFormat.channels,
                    baseFormat.channels * 2,
                    baseFormat.sampleRate,
                    false
                )

                val decodedStream =
                    AudioSystem.getAudioInputStream(decodedFormat, originalStream)

                val c = AudioSystem.getClip()
                c.open(decodedStream)
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