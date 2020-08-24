package graphics

import graphics.model.BadMapFileException
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.*
import utils.Settings as set

class LoadMapMenu() : JFrame("[RGlove 1.0] Please choose map mode! ") {

    private var map : Map? = null
    var isMapLoaded = false

    val fileChooser = JFileChooser()

    fun createMap(file: File?): Map {
        return if (file == null) {
            Map()
        } else {
            Map(file)
        }
    }

    fun getMap(): Map {
        return if (map == null) {
            Map()
        } else {
            map as Map
        }
    }

    inner class SimpleAction : AbstractAction() {

        override fun actionPerformed(e: ActionEvent) {

            val btn = e.source as JButton

            if (btn.name.equals(set.MAP_LOAD_FIRST_BUTTON_NAME, ignoreCase = true)) {
                map = Map()
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
                    map = createMap(selectedFile)
                } catch (e : BadMapFileException) {
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
        val button1 = JButton(action)

        button1.name = set.MAP_LOAD_FIRST_BUTTON_NAME
        button1.text = set.MAP_LOAD_FIRST_BUTTON_NAME

        val button2 = JButton(action)
        button2.name = set.MAP_LOAD_SECOND_BUTTON_NAME
        button2.text = set.MAP_LOAD_SECOND_BUTTON_NAME

        container.add(button1)
        container.add(button2)

        setSize(320, 100)
        isVisible = true
    }
}