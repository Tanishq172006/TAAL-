package org.example.project

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MetronomeEngine {

    private var job: Job? = null

    var bpm = 120

    private val _step = MutableStateFlow(0)
    val step: StateFlow<Int> = _step

    fun start() {

        if (job != null) return

        job = CoroutineScope(Dispatchers.Default).launch {

            val stepDuration = 60000 / (bpm * 4)

            while (true) {

                _step.value = (_step.value + 1) % 16

                delay(stepDuration.toLong())
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
        _step.value = 0
    }
}