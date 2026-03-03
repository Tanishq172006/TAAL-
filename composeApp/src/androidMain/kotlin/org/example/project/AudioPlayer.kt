package org.example.project

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import java.util.concurrent.CopyOnWriteArrayList

actual class AudioPlayer(
    private val context: Context
) {
    private val activePlayers = CopyOnWriteArrayList<ExoPlayer>()

    actual fun play(resourceName: String) {
        val name = resourceName.removeSuffix(".wav").removeSuffix(".mp3").lowercase()
        
        // Try to find the resource ID by name in the 'raw' folder
        val resId = context.resources.getIdentifier(name, "raw", context.packageName)

        if (resId == 0) {
            println("AudioPlayer: Resource not found: $name")
            return
        }

        val uri = "android.resource://${context.packageName}/$resId"

        val player = ExoPlayer.Builder(context.applicationContext).build()
        activePlayers.add(player)

        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()
        player.playWhenReady = true

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    player.release()
                    activePlayers.remove(player)
                }
            }
        })
    }

    actual fun pause() {
        activePlayers.forEach { it.pause() }
    }

    actual fun stop() {
        activePlayers.forEach {
            it.stop()
            it.release()
        }
        activePlayers.clear()
    }

    actual fun release() {
        stop()
    }

    actual val isPlaying: Boolean
        get() = activePlayers.any { it.isPlaying }
}

actual fun createAudioPlayer(): AudioPlayer {
    val ctx = AppContextHolder.context
        ?: throw IllegalStateException("AppContextHolder.context not set in MainActivity")
    return AudioPlayer(ctx)
}
