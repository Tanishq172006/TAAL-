package org.example.project
import platform.AVFAudio.AVAudioPlayer
import platform.AVFoundation.*
import platform.Foundation.*



@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual class AudioPlayer actual constructor() {

    actual fun playSound(name: String) {
        val fileName = when (name) {
            "drum" -> "drum"
            "guitar" -> "guitar"
            "sax" -> "sax"
            else -> return
        }

        val path = NSBundle.mainBundle.pathForResource(fileName, "wav") ?: return
        val url = NSURL.fileURLWithPath(path)

        val player = AVAudioPlayer(contentsOfURL = url, error = null)
        player?.play()
    }
}