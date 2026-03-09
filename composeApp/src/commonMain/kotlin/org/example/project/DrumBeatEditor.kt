package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun DrumBeatEditor(
    state: DrumEditorState,
    audioPlayer: AudioPlayer,
    onSave: () -> Unit,
    onClose: () -> Unit
) {

    var playing by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val drumFiles = listOf(
        "kick.wav",
        "snare.wav",
        "closedhat.wav",
        "openhat.wav",
        "tom.wav",
        "crash.wav",
        "ride.wav",
        "clap.wav"
    )

    val drumNames = listOf(
        "Kick",
        "Snare",
        "Closed Hat",
        "Open Hat",
        "Tom",
        "Crash",
        "Ride",
        "Clap"
    )

    LaunchedEffect(playing) {

        if (!playing) return@LaunchedEffect

        val bpm = 60
        val stepDuration = 60000L / (bpm * 4)

        while (playing) {

            state.grid.forEachIndexed { row, steps ->

                if (steps[state.playhead]) {
                    audioPlayer.playSound(drumFiles[row])
                }
            }

            delay(stepDuration)

            state.playhead = (state.playhead + 1) % state.cols
        }
    }

    Column(
        modifier = Modifier
            .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {

        Text(
            "Drum Beat Editor",
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        state.grid.forEachIndexed { rowIndex, row ->

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {

                Text(
                    drumNames[rowIndex],
                    color = Color.White,
                    modifier = Modifier
                        .width(90.dp)
                        .padding(end = 6.dp)
                )

                Row(
                    modifier = Modifier.horizontalScroll(scrollState)
                ) {

                    row.forEachIndexed { colIndex, active ->

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(2.dp)
                                .background(
                                    when {
                                        colIndex == state.playhead -> Color.Yellow
                                        active -> Color.Green
                                        else -> Color.DarkGray
                                    }
                                )
                                .clickable {
                                    state.toggle(rowIndex, colIndex)
                                }
                        )
                    }
                }
            }

            Spacer(Modifier.height(6.dp))
        }

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()

        ) {

            Text(
                if (playing) "Stop" else "Play",
                color = Color.Yellow,
                modifier = Modifier.clickable {
                    playing = !playing
                }
            )

            Text(
                "Clear",
                color = Color.Cyan,
                modifier = Modifier.clickable {
                    state.clear()
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                "Cancel",
                color = Color.Red,
                modifier = Modifier.clickable {
                    playing = false
                    onClose()
                }
            )

            Text(
                "Save Beat",
                color = Color.Green,
                modifier = Modifier.clickable {

                    playing = false

                    onSave()
                }
            )
        }
    }
}