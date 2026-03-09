package org.example.project

import kotlinx.coroutines.*
import pianoNotes

class StepSequencer(
    private val metronome: MetronomeEngine,
    private val audioPlayer: AudioPlayer
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    private var job: Job? = null

    private val activeDrumPatterns = mutableListOf<DrumEditorState>()
    private val activePianoPatterns = mutableListOf<PianoEditorState>()

    fun start() {

        if (job != null) return

        job = scope.launch {

            metronome.step.collect { step ->

                playDrums(step)
                playPianos(step)

            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    fun addDrumPattern(pattern: DrumEditorState) {
        activeDrumPatterns.add(pattern)
    }

    fun addPianoPattern(pattern: PianoEditorState) {
        activePianoPatterns.add(pattern)
    }

    private fun playDrums(step: Int) {

        val drumFiles = listOf(
            "kick.wav","snare.wav","closedhat.wav",
            "openhat.wav","tom.wav","crash.wav",
            "ride.wav","clap.wav"
        )

        activeDrumPatterns.forEach { pattern ->

            pattern.grid.forEachIndexed { row, steps ->
                if (steps[step]) {
                    audioPlayer.playSound(drumFiles[row])
                }
            }

        }
    }

    private fun playPianos(step: Int) {

        activePianoPatterns.forEach { pattern ->

            pattern.grid.forEachIndexed { row, steps ->
                if (steps[step]) {
                    audioPlayer.playSound(pianoNotes[row])
                }
            }

        }
    }
}