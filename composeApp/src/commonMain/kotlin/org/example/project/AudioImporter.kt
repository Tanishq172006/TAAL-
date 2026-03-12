package org.example.project

expect class AudioImporter() {
    fun pickAudio(onResult: (String) -> Unit)
}