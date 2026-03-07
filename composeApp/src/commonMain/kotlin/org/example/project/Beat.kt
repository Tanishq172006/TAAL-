package org.example.project

data class Beat(
    val id: String,
    val name: String,
    val fileName: String? = null,
    val steps: MutableList<Boolean> = MutableList(16) { false },
    val drumPattern: DrumEditorState? = null,
    val pianoPattern: PianoEditorState? = null
) {

    fun deepCopy(): Beat {
        return Beat(
            id = id,
            name = name,
            fileName = fileName,
            steps = steps.toMutableList(),
            drumPattern = drumPattern?.deepCopy(),
            pianoPattern = pianoPattern?.deepCopy()
        )
    }
}
