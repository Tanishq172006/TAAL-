import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.AudioPlayer
import org.example.project.PianoEditorState

val pianoNotes = listOf(
    "piano_a2.wav",
    "piano_a3.wav",
    "piano_a4.wav",
    "piano_b2.wav",
    "piano_b3.wav",
    "piano_b4.wav",
    "piano_c2.wav",
    "piano_c3.wav",
    "piano_b4.wav",
    "piano_c5.wav",
    "piano_d2.wav",
    "piano_d3.wav",
    "piano_d4.wav",
    "piano_e2.wav",
    "piano_e3.wav",
    "piano_e4.wav",
    "piano_f2.wav",
    "piano_f3.wav",
    "piano_f4.wav",
    "piano_g2.wav",
    "piano_g3.wav",
    "piano_g4.wav"
)

@Composable
fun PianoRollEditor(
    state: PianoEditorState,
    audioPlayer: AudioPlayer,
    onSave: () -> Unit,
    onClose: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF444444), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, null)
            }

            Row {

                IconButton(onClick = onSave) {
                    Icon(Icons.Default.Save, null)
                }

                IconButton(onClick = {}) {
                    Icon(Icons.Default.Add, null)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxSize()) {

            VerticalPianoKeys(audioPlayer)

            Spacer(Modifier.width(8.dp))

            StepMatrix(
                state = state,
                audioPlayer = audioPlayer,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StepMatrix(
    state: PianoEditorState,
    audioPlayer: AudioPlayer,
    modifier: Modifier = Modifier
) {

    val horizontalScroll = rememberScrollState()
    val verticalScroll = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(verticalScroll)
            .horizontalScroll(horizontalScroll)
    ) {

        Column {

            repeat(state.rows) { row ->

                Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {

                    repeat(state.cols) { column ->

                        val active = state.grid[row][column]

                        val backgroundColor =
                            when {
                                active && column == state.playhead -> Color.Yellow
                                active -> Color(0xFFFF6666)
                                column == state.playhead -> Color(0xFF666666)
                                column % 16 == 0 -> Color(0xFF505050)
                                column % 4 == 0 -> Color(0xFF3A3A3A)
                                else -> Color(0xFF2E2E2E)
                            }

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(backgroundColor, RoundedCornerShape(4.dp))
                                .clickable {

                                    state.toggle(row, column)

                                    if (state.grid[row][column]) {
                                        audioPlayer.playSound(pianoNotes[row])
                                    }
                                }
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun VerticalPianoKeys(audioPlayer: AudioPlayer) {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        pianoNotes.forEach { note ->

            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(24.dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
                    .clickable {
                        audioPlayer.playSound(note)
                    }
            )
        }
    }
}