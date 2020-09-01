package com.roguelike.commands

import com.roguelike.graphics.GameMap
import com.roguelike.graphics.map_loading.BadMapFileException
import java.io.File
import javax.swing.JFileChooser

/** load map from file and updates given gameMap **/
class LoadMapCommand(private val fileChooser: JFileChooser,
        private var gameMap: GameMap?): Command() {

    override fun execute(): Boolean {
        var selectedFile: File? = null
        fileChooser.currentDirectory = File(System.getProperty("user.dir"))

        val result = fileChooser.showOpenDialog(fileChooser)
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.selectedFile
        }

        try {
            gameMap = createMap(selectedFile)
        } catch (e: BadMapFileException) {
            return false
        }

        return true
    }

    /** create map or generate from file */
    private fun createMap(file: File?): GameMap {
        return if (file == null) {
            GameMap()
        } else {
            GameMap(file)
        }
    }
}
