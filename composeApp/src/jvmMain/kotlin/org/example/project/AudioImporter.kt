package org.example.project

actual class AudioImporter {

    actual fun pickAudio(onAudioPicked: (String) -> Unit) {

        val chooser = javax.swing.JFileChooser()

        chooser.fileFilter = javax.swing.filechooser.FileNameExtensionFilter(
            "Audio Files",
            "wav",
            "mp3",
            "ogg"
        )

        val result = chooser.showOpenDialog(null)

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            onAudioPicked(chooser.selectedFile.absolutePath)
        }

    }
}