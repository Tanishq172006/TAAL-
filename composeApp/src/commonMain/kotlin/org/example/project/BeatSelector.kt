package org.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BeatSelector(
    beats: List<Beat>,
    audioPlayer: AudioPlayer,
    onSaveToTile: (Beat) -> Unit,
    onCreateNewTile: (Beat) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedBeat by remember { mutableStateOf<Beat?>(null) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .width(350.dp)
        ) {

            Text(
                "Select Default Beat",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(beats) { beat ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedBeat = beat
                                audioPlayer.play(beat.fileName)
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = selectedBeat == beat,
                            onClick = {
                                selectedBeat = beat
                                audioPlayer.play(beat.fileName)
                            }
                        )

                        Spacer(Modifier.width(12.dp))

                        Text(beat.name)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        selectedBeat?.let { onSaveToTile(it) }
                    },
                    enabled = selectedBeat != null
                ) {
                    Text("Save to Tile")
                }

                Button(
                    onClick = {
                        selectedBeat?.let { onCreateNewTile(it) }
                    },
                    enabled = selectedBeat != null
                ) {
                    Text("Create New")
                }
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    }
}