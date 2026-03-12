package org.example.project

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.example.project.database.DriverFactory
import org.example.project.database.Patterns
import org.example.project.database.Songs
import org.example.project.database.createDatabase
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class Note(
    val tick: Int,
    val pitch: Int,
    val length: Int,
    val velocity: Int
)
@Serializable
data class Pattern(
    val notes: List<Note>
)

class MusicDatabaseRepository(driverFactory: DriverFactory) {
    private val d = createDatabase(driverFactory)
    fun insertSong(name: String, bpm: Long, date: Long) {
        d.insertSong(name, bpm, date)
    }
    fun getSongId(): Long{
        return 0
    }
    fun insertTrack(songId: Long, instrument: String, volume: Double) {
        d.insertTrack(songId, instrument, volume)
    }
    fun insertPattern(songId: Long, trackId: Long ,name: String, len: Long, curPat: Note) {
        val existingRow = d.getPatternsForSong(songId, trackId).executeAsOneOrNull()
        val updatedNotes = if (existingRow != null) {
            val currentPattern = Json.decodeFromString<Pattern>(existingRow.pattern_data)
            currentPattern.notes + curPat
        } else {
            listOf(curPat)
        }
        val newPatternJson = Json.encodeToString(Pattern(notes = updatedNotes))
        d.insertPattern(songId, trackId, name, len, newPatternJson)
    }
    fun displaySongs(): Flow<List<Songs>> =
        d.selectAllSongs().asFlow().mapToList(Dispatchers.Default)

    fun getAllPattern(songId: Long, trackId: Long): Flow<List<Patterns>> =
        d.getPatternsForSong(songId, trackId ).asFlow().mapToList(Dispatchers.Default)

    fun getAllPatternData(songId: Long, trackId: Long): Flow<List<String>> =
        d.getAllPatternData(songId, trackId ).asFlow().mapToList(Dispatchers.Default)
    }