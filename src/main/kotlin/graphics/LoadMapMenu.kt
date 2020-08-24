package graphics

import graphics.model.BadMapFileException
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.*
import utils.Settings as set

/** start menu. Map loading logic */
class LoadMapMenu : JFrame("[RGlove 1.0] Please choose map mode! ") {
    private var gameMap: GameMap? = null
    var isMapLoaded = false
    private val fileChooser = JFileChooser()

    // create map or generate from file
    fun createMap(file: File?): GameMap {
        return if (file == null) {
            GameMap()
        } else {
            GameMap(file)
        }
    }

    fun getMap(): GameMap {
        return if (gameMap == null) {
            GameMap()
        } else {
            gameMap as GameMap
        }
    }

    inner class SimpleAction : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            val btn = e.source as JButton

            if (btn.name.equals(set.MAP_LOAD_FIRST_BUTTON_NAME, ignoreCase = true)) {
                gameMap = GameMap()
                isMapLoaded = true
                this@LoadMapMenu.isVisible = false
            }

            if (btn.name.equals(set.MAP_LOAD_SECOND_BUTTON_NAME, ignoreCase = true)) {
                var selectedFile: File? = null
                fileChooser.currentDirectory = File(System.getProperty("user.home"));

                val result = fileChooser.showOpenDialog(fileChooser)
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.selectedFile
                }

                try {
                    gameMap = createMap(selectedFile)
                } catch (e: BadMapFileException) {
                    this@LoadMapMenu.title = "[Bad map file] Try again! "
                    return
                }

                isMapLoaded = true
                this@LoadMapMenu.isVisible = false
            }
        }
    }

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        val container = contentPane
        container.layout = FlowLayout()

        val action: Action = SimpleAction()
        val randomMapButton = JButton(action)

        randomMapButton.name = set.MAP_LOAD_FIRST_BUTTON_NAME
        randomMapButton.text = set.MAP_LOAD_FIRST_BUTTON_NAME

        val mapFromFileButton = JButton(action)
        mapFromFileButton.name = set.MAP_LOAD_SECOND_BUTTON_NAME
        mapFromFileButton.text = set.MAP_LOAD_SECOND_BUTTON_NAME

        container.add(randomMapButton)
        container.add(mapFromFileButton)

        setSize(320, 100)
        isResizable = false
        isVisible = true
    }
}
