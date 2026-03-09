package org.example.project

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BeatEditorViewModel {

    private val _steps = MutableStateFlow<Map<Pair<Int, Int>, Boolean>>(emptyMap())

    val steps: StateFlow<Map<Pair<Int, Int>, Boolean>> = _steps

    fun toggleStep(instrumentIndex: Int, stepIndex: Int) {
        _steps.update { current ->
            val newMap = current.toMutableMap()
            val key = instrumentIndex to stepIndex

            newMap[key] = !(newMap[key] ?: false)
            newMap
        }
    }
}