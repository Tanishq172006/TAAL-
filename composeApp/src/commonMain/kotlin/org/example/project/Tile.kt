package org.example.project

data class Tile(
    val id: Int,
    val instrument: InstrumentType,
    val beat: Beat? = null
)