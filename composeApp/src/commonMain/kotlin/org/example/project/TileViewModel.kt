import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.example.project.Beat
import org.example.project.InstrumentCategory
import org.example.project.InstrumentType
import org.example.project.Tile
import org.example.project.ui.theme.*
import taal.composeapp.generated.resources.*

class TileViewModel {

    private var nextId = 0

    var categories by mutableStateOf(
        listOf(
            createCategory("Drums", "drum", DrumRed, Res.drawable.drum),
            createCategory("Guitars", "guitar", GuitarOrange, Res.drawable.guitar),
            createCategory("Sax", "sax", SaxYellow, Res.drawable.saxophone),
            createCategory("Electric Guitar", "electric_guitar", ElectricGuitarDGreen, Res.drawable.electric_guitar),
            createCategory("Flute", "flute", flutePurple, Res.drawable.flute),
            createCategory("Piano", "piano", pianoBlue, Res.drawable.piano),
            createCategory("Harmonium", "harmonium", harmoniumColor, Res.drawable.harmonium),
            createCategory("Violin", "violin", violinPink, Res.drawable.violin)
        )
    )
        private set

    private fun createCategory(
        title: String,
        name: String,
        color: Color,
        icon: DrawableResource
    ): InstrumentCategory {
        return InstrumentCategory(
            title = title,
            tiles = List(40) {
                Tile(
                    id = nextId++,
                    instrument = InstrumentType(
                        name = name,
                        color = color,
                        iconRes = icon
                    )
                )
            }
        )
    }

    fun assignBeat(categoryTitle: String, tileId: Int, beat: Beat) {
        categories = categories.map { category ->
            if (category.title == categoryTitle) {
                category.copy(
                    tiles = category.tiles.map { tile ->
                        if (tile.id == tileId)
                            tile.copy(beat = beat)
                        else tile
                    }
                )
            } else category
        }
    }

    fun addTile(categoryTitle: String, baseTile: Tile, beat: Beat) {
        categories = categories.map { category ->
            if (category.title == categoryTitle) {
                category.copy(
                    tiles = category.tiles + Tile(
                        id = nextId++,
                        instrument = baseTile.instrument,
                        beat = beat
                    )
                )
            } else category
        }
    }
}