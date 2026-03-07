package org.example.project

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

actual class AudioPlayer(context: Context) {

    private val soundPool: SoundPool
    private val sounds = mutableMapOf<String, Int>()
    private val appContext = context.applicationContext

    init {

        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(16)
            .setAudioAttributes(attrs)
            .build()
    }

    actual fun playSound(name: String) {

        val id = sounds[name] ?: loadSound(name)

        soundPool.play(
            id,
            1f,
            1f,
            1,
            0,
            1f
        )
    }

    private fun loadSound(name: String): Int {

        val fileName = name.substringBefore(".")

        val resId = appContext.resources.getIdentifier(
            fileName,
            "raw",
            appContext.packageName
        )

        if (resId == 0) {
            throw IllegalArgumentException("Audio file not found in res/raw: $fileName")
        }

        val soundId = soundPool.load(appContext, resId, 1)

        sounds[name] = soundId

        return soundId
    }
}