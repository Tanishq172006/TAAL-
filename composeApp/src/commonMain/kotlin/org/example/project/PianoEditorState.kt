package org.example.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PianoEditorState {

    val rows = 22
    val cols = 128
    var playhead by mutableStateOf(0)

    var grid by mutableStateOf(
        List(rows) { MutableList(cols) { false } }
    )

    fun toggle(row: Int, col: Int) {
        grid = grid.toMutableList().apply {
            this[row] = this[row].toMutableList().apply {
                this[col] = !this[col]
            }
        }
    }

    fun clear() {
        grid = List(rows) { MutableList(cols) { false } }
    }

    fun deepCopy(): PianoEditorState {
        val newState = PianoEditorState()

        newState.grid = grid.map { row ->
            row.toMutableList()
        }

        newState.playhead = playhead

        return newState
    }
}