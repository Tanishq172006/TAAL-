package org.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pianoNotes

class StepSequencer(
    private val metronome: MetronomeEngine,
    private val audioPlayer: AudioPlayer
) {

    private val activeDrumPatterns = mutableListOf<DrumEditorState>()
    private val activePianoPatterns = mutableListOf<PianoEditorState>()

    fun start() {

        CoroutineScope(Dispatchers.Default).launch {

            metronome.step.collect { step ->

                playDrums(step)
                playPianos(step)

            }
        }
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