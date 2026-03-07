package org.example.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DrumEditorState {

    val rows = 8
    val cols = 16

    var grid by mutableStateOf(
        List(rows) { MutableList(cols) { false } }
    )

    var playhead by mutableStateOf(0)

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


    fun deepCopy(): DrumEditorState {
        val newState = DrumEditorState()

        newState.grid = grid.map { row ->
            row.toMutableList()
        }

        newState.playhead = playhead

        return newState
    }
}