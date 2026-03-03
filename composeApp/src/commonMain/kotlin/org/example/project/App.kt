package org.example.project

import TileViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import taal.composeapp.generated.resources.Res
import taal.composeapp.generated.resources.compose_multiplatform
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import org.example.project.ui.theme.*
import org.jetbrains.compose.resources.DrawableResource
import taal.composeapp.generated.resources.drum
import taal.composeapp.generated.resources.electric_guitar
import taal.composeapp.generated.resources.flute
import taal.composeapp.generated.resources.guitar
import taal.composeapp.generated.resources.harmonium
import taal.composeapp.generated.resources.piano
import taal.composeapp.generated.resources.saxophone
import taal.composeapp.generated.resources.violin

@Composable
@Preview
fun App() {
    MaterialTheme {
        MusicPadScreen()
    }
}


@Composable
fun MusicPadScreen(){
    val viewModel = remember { TileViewModel() }

    var showBeatSelector by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedTile by remember { mutableStateOf<Tile?>(null) }

    val beats = remember {
        listOf(
            Beat("b1", "Classic Beat", "drum.wav"),
            Beat("b2", "Rock Beat", "rock.wav"),
            Beat("b3", "Jazz Beat", "jazz.wav")
        )
    }

    val audioPlayer = remember { createAudioPlayer() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TopBar()

            Spacer(Modifier.height(16.dp))

            SoundGrid(
                categories = viewModel.categories,
                audioPlayer = audioPlayer,
                modifier = Modifier.weight(1f),
                onLongPress = { categoryTitle, tile ->
                    selectedCategory = categoryTitle
                    selectedTile = tile
                    showBeatSelector = true
                }
            )
        }


        BottomControls(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        if (showBeatSelector && selectedTile != null) {
            Dialog(onDismissRequest = { showBeatSelector = false }) {
                BeatSelector(
                    beats = beats,
                    audioPlayer = audioPlayer,
                    onSaveToTile = { beat ->
                        viewModel.assignBeat(
                            selectedCategory!!,
                            selectedTile!!.id,
                            beat
                        )
                        showBeatSelector = false
                    },
                    onCreateNewTile = { beat ->
                        viewModel.addTile(
                            selectedCategory!!,
                            selectedTile!!,
                            beat
                        )
                        showBeatSelector = false
                    },
                    onDismiss = { showBeatSelector = false }
                )
            }
        }
    }
}



@Composable
fun TopBar()
{
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton({}) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
        }

        Row {
            IconButton({}) { Icon(Icons.Default.Mic, null, tint = Color.White) }
            IconButton({}) { Icon(Icons.Default.MusicNote, null, tint = Color.White) }
            IconButton({}) { Icon(Icons.Default.VolumeUp, null, tint = Color.White) }
            IconButton({}) { Icon(Icons.Default.Menu, null, tint = Color.White) }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SoundGrid(
    categories: List<InstrumentCategory>,
    audioPlayer: AudioPlayer,
    modifier: Modifier = Modifier,
    onLongPress: (String, Tile) -> Unit
){

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        items(categories.size) { categoryIndex ->

            val category = categories[categoryIndex]

            Column {

                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp)
                )


                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {

                    items(category.tiles.size) { index ->

                        val tile = category.tiles[index]

                        SoundPad(
                            color = tile.instrument.color,
                            icon = painterResource(tile.instrument.iconRes),
                            onClick = {
                                tile.beat?.let { beat ->
                                    audioPlayer.play(beat.fileName.lowercase())   // play the assigned beat sound
                                } ?: run {
                                    audioPlayer.play(tile.instrument.name.lowercase())  // fallback to instrument default
                                }
                            },

                            onLongPress = {
                                onLongPress(category.title, tile)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SoundPad(
    color: Color,
    icon: Painter,
    onClick: () -> Unit,
    onLongPress: () -> Unit
){
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        label = ""
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .combinedClickable(
                onClick = {
                    pressed = true
                    onClick()
                },
                onLongClick = {
                    onLongPress()
                }
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(80)
            pressed = false
        }
    }
}

@Composable
fun BottomControls(modifier: Modifier = Modifier) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = BottomBarColor,
        tonalElevation = 8.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Icon(Icons.Default.GraphicEq, null, tint = Color.White)
            Icon(Icons.Default.Piano, null, tint = Color.White)
        }
    }
}




