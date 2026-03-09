package org.example.project

import PianoRollEditor
import TileViewModel
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import org.example.project.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import pianoNotes
import taal.composeapp.generated.resources.Res
@Composable
fun App(
    audioPlayer: AudioPlayer,
    authRepository: org.example.project.auth.AuthRepository,
    onGoogleSignInClick: () -> Unit
) {
    var currentScreen by rememberSaveable { mutableStateOf("standards") }
    val beatEditorState = rememberBeatEditorState()
    val audioImporter = remember { AudioImporter() }
    val tileViewModel = remember { TileViewModel() }
    val metronome = remember { MetronomeEngine() }
    val sequencer = remember { StepSequencer(metronome, audioPlayer) }

    var showAuthScreen by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = DarkBackground) {
            Box(Modifier.fillMaxSize()) {
                when (currentScreen) {
                    "standards" -> {
                        StandardsScreen(
                            onNavigateToProjects = { currentScreen = "projects" }
                        )
                    }

                    "projects" -> {
                        ProjectSelectionScreen(
                            onNavigateToMusic = { currentScreen = "music_pad" },
                            onNavigateBack = { currentScreen = "standards" }
                        )
                    }

                    "music_pad" -> {
                        MusicPadScreen(
                            state = beatEditorState,
                            onNavigateBack = { currentScreen = "projects" },
                            audioImporter = audioImporter,
                            tileViewModel = tileViewModel,
                            audioPlayer = audioPlayer,
                            metronome = metronome,
                            sequencer = sequencer
                        )
                    }
                }

                ProfileIcon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    onClick = {
                        showAuthScreen = true
                    }
                )

                if (showAuthScreen) {
                    Dialog(onDismissRequest = { showAuthScreen = false }) {
                        LoginSignupScreen(
                            authRepository = authRepository,
                            onGoogleSignInClick = onGoogleSignInClick,
                            onLoginSuccess = {
                                isLoggedIn = true
                                showAuthScreen = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MusicPadScreen(
    state: BeatEditorState,
    onNavigateBack: () -> Unit,
    audioImporter: AudioImporter,
    tileViewModel: TileViewModel,
    audioPlayer: AudioPlayer,
    metronome: MetronomeEngine,
    sequencer: StepSequencer
){
    var showAudioEditor by remember { mutableStateOf(false) }
    var drumEditorState by remember { mutableStateOf<DrumEditorState?>(null) }
    var playing by remember { mutableStateOf(false) }
    var pianoEditorState by remember { mutableStateOf<PianoEditorState?>(null) }
    var metronomeRunning by remember { mutableStateOf(false) }
    var isEditorMode by remember { mutableStateOf(false) }
    var showBeatSelector by remember { mutableStateOf(false) }
    var showPianoEditor by remember { mutableStateOf(false) }
    var showDrumEditor by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedTile by remember { mutableStateOf<Tile?>(null) }

    val beats = remember {
        listOf(
            Beat("b1", "Classic Beat", "drum.wav"),
            Beat("b2", "Rock Beat", "rock.wav"),
            Beat("b3", "Jazz Beat", "jazz.wav")
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(DarkBackground)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            TopBar(
                onBackClick = onNavigateBack,
                metronome = metronome,
                metronomeRunning = metronomeRunning,
                onToggleMetronome = {
                    metronomeRunning = !metronomeRunning
                    if (metronomeRunning) {
                        metronome.start()
                        sequencer.start()
                    } else {
                        metronome.stop()
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            if (isEditorMode) {
                SoundGrid(
                    categories = tileViewModel.categories,
                    audioPlayer = audioPlayer,
                    modifier = Modifier.weight(1f),
                    metronome = metronome,
                    sequencer = sequencer,
                    onLongPress = { categoryTitle, tile ->
                        selectedCategory = categoryTitle
                        selectedTile = tile
                        if (tile.instrument.name == "piano") {
                            pianoEditorState = tile.beat?.pianoPattern ?: PianoEditorState()
                            showPianoEditor = true
                        } else if (tile.instrument.name == "drum") {
                            drumEditorState = tile.beat?.drumPattern ?: DrumEditorState()
                            showDrumEditor = true
                        } else {
                            showBeatSelector = true
                        }
                    }
                )
            } else {
                BeatEditorScreen(
                    categories = tileViewModel.categories,
                    state = state,
                    modifier = Modifier.weight(1f),
                    onTileLongPress = { instrumentIndex, stepIndex ->
                        val category = tileViewModel.categories[instrumentIndex]
                        val tile = category.tiles[stepIndex]
                        selectedCategory = category.title
                        selectedTile = tile
                        if (tile.instrument.name == "piano") {
                            showPianoEditor = true
                        } else {
                            showAudioEditor = true
                        }
                    }
                )
            }
        }

        BottomControls(
            isEditorMode = isEditorMode,
            onToggle = { isEditorMode = !isEditorMode },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp)
        )

        if (showBeatSelector && selectedTile != null) {
            Dialog(onDismissRequest = { showBeatSelector = false }) {
                Column(modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp)).padding(16.dp)) {
                    BeatSelector(
                        beats = beats,
                        audioPlayer = audioPlayer,
                        onSaveToTile = { beat ->
                            tileViewModel.assignBeat(selectedCategory!!, selectedTile!!.id, beat)
                            showBeatSelector = false
                        },
                        onCreateNewTile = { beat ->
                            tileViewModel.addTile(selectedCategory!!, selectedTile!!, beat)
                            showBeatSelector = false
                        },
                        onImportAudio = {
                            audioImporter.pickAudio { path ->
                                tileViewModel.assignBeat(selectedCategory!!, selectedTile!!.id, Beat("imported", "Imported Audio", path))
                                showBeatSelector = false
                            }
                        },
                        onDismiss = { showBeatSelector = false }
                    )
                }
            }
        }

        if (showPianoEditor && selectedTile != null) {
            Dialog(onDismissRequest = { showPianoEditor = false }) {
                Box(modifier = Modifier.fillMaxWidth(0.95f).fillMaxHeight(0.8f)) {
                    PianoRollEditor(
                        state = pianoEditorState ?: PianoEditorState(),
                        audioPlayer = audioPlayer,
                        onSave = {
                            tileViewModel.assignBeat(selectedCategory!!, selectedTile!!.id, Beat("piano_${Clock.System.now().toEpochMilliseconds()}", "Piano Pattern", pianoPattern = pianoEditorState))
                            showPianoEditor = false
                        },
                        onClose = { showPianoEditor = false }
                    )
                }
            }
        }

        if (showDrumEditor && selectedTile != null && drumEditorState != null) {
            Dialog(onDismissRequest = { showDrumEditor = false }) {
                Column {
                    DrumBeatEditor(
                        state = drumEditorState!!,
                        audioPlayer = audioPlayer,
                        onSave = {
                            tileViewModel.assignBeat(selectedCategory!!, selectedTile!!.id, Beat("custom_${Clock.System.now().toEpochMilliseconds()}", "Custom Beat", drumPattern = drumEditorState!!))
                            showDrumEditor = false
                        },
                        onClose = { showDrumEditor = false }
                    )
                }
            }
        }

        if (showAudioEditor && selectedTile != null) {
            Dialog(onDismissRequest = { showAudioEditor = false }) {
                AudioEditor(
                    fileName = selectedTile!!.beat?.fileName ?: selectedTile!!.instrument.name,
                    onClose = { showAudioEditor = false },
                    onSave = { fileName ->
                        tileViewModel.assignBeat(selectedCategory!!, selectedTile!!.id, Beat("edited", "Edited Beat", fileName))
                        showAudioEditor = false
                    }
                )
            }
        }
    }
}

@Composable
fun TopBar(
    onBackClick: () -> Unit,
    metronome: MetronomeEngine,
    metronomeRunning: Boolean,
    onToggleMetronome: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        Box(modifier = Modifier.background(Color.DarkGray, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
            Text(text = "00:00:00", color = Color.White, fontWeight = FontWeight.Medium)
        }
        Row {
            IconButton({}) { Icon(Icons.Default.Mic, null, tint = Color.White) }
            IconButton(onClick = onToggleMetronome) {
                Icon(imageVector = Icons.Default.Speed, contentDescription = "Metronome", tint = if (metronomeRunning) Color.Green else Color.White)
            }
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
    metronome: MetronomeEngine,
    sequencer: StepSequencer,
    modifier: Modifier = Modifier,
    onLongPress: (String, Tile) -> Unit
){
    var activeTiles by remember { mutableStateOf(setOf<Int>()) }
    val currentStep by metronome.step.collectAsState()

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        items(categories.size) { categoryIndex ->
            val category = categories[categoryIndex]
            Column {
                Text(text = category.title, style = MaterialTheme.typography.titleMedium, color = Color.White, modifier = Modifier.padding(bottom = 12.dp))
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                ) {
                    items(category.tiles.size) { index ->
                        val tile = category.tiles[index]
                        val column = index / 2
                        SoundPad(
                            color = tile.instrument.color,
                            icon = painterResource(tile.instrument.iconRes),
                            isActive = tile.id in activeTiles,
                            isPlayhead = column == currentStep,
                            onClick = {
                                activeTiles = activeTiles + tile.id
                                CoroutineScope(Dispatchers.Main).launch {
                                    val beat = tile.beat
                                    if (beat?.pianoPattern != null) sequencer.addPianoPattern(beat.pianoPattern)
                                    else if (beat?.drumPattern != null) sequencer.addDrumPattern(beat.drumPattern)
                                    else if (beat?.fileName != null) audioPlayer.playSound(beat.fileName)
                                    else {
                                        when (tile.instrument.name) {
                                            "drum" -> audioPlayer.playSound("kick.wav")
                                            "piano" -> audioPlayer.playSound("piano_c4.wav")
                                            "harmonium" -> audioPlayer.playSound("piano_c3.wav")
                                            "violin" -> audioPlayer.playSound("piano_g4.wav")
                                        }
                                    }
                                    delay(400)
                                    activeTiles = activeTiles - tile.id
                                }
                            },
                            onLongPress = { onLongPress(category.title, tile) }
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
    isActive: Boolean,
    isPlayhead: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit
){
    var pressed by remember { mutableStateOf(false) }
    val animatedColor by animateColorAsState(if (isActive) Color.White else color, label = "")
    val scale by animateFloatAsState(targetValue = if (pressed) 0.92f else 1f, label = "")

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(20.dp))
            .background(when { isPlayhead -> Color.White.copy(alpha = 0.25f); isActive -> animatedColor; else -> color })
            .border(if (isActive) 3.dp else 0.dp, Color.White, RoundedCornerShape(20.dp))
            .combinedClickable(onClick = { pressed = true; onClick() }, onLongClick = { onLongPress() })
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = icon, contentDescription = null, modifier = Modifier.size(40.dp))
    }
    LaunchedEffect(pressed) { if (pressed) { delay(150); pressed = false } }
}

@Composable
fun BottomControls(isEditorMode: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(24.dp), color = BottomBarColor, tonalElevation = 8.dp) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = if (!isEditorMode) Color.White else Color.Gray, modifier = Modifier.clickable { onToggle() })
            Icon(imageVector = Icons.Default.Piano, contentDescription = null, tint = if (isEditorMode) Color.White else Color.Gray, modifier = Modifier.clickable { onToggle() })
        }
    }
}