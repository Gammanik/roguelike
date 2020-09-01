package com.roguelike.graphics.map_loading


import com.roguelike.GamePanel
import com.roguelike.commands.LoadGameCommand
import com.roguelike.commands.LoadMapCommand
import com.roguelike.commands.RandomMapCommand
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.*
import com.roguelike.utils.Settings as set

/** start menu. Map loading logic */
class LoadMapMenu(private val playerDeadCb: () -> Unit) : JFrame("[RGlove 1.0] Please choose map mode! ") {
    private var gameMap: GameMap? = null
    var isMapLoaded = false; private set
    private val fileChooser = JFileChooser()

    private var gamePanel: GamePanel? = null

    /** get map or create if yet not created */
    private fun getMap(): GameMap {
        return if (gameMap == null)
            GameMap()
        else gameMap!!
    }

    /** inner class for handling start menu button click
     * load class from file of load random map */
    inner class SimpleAction : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            val btn = e.source as JButton

            if (btn.name.equals(set.MAP_LOAD_FIRST_BUTTON_NAME, ignoreCase = true)) {
                RandomMapCommand(gameMap).execute()
                isMapLoaded = true
                isVisible = false
            }

            if (btn.name.equals(set.MAP_LOAD_SECOND_BUTTON_NAME, ignoreCase = true)) {
                if (LoadMapCommand(fileChooser, gameMap).execute()) {
                    isMapLoaded = true
                    isVisible = false
                } else {
                    title = "[Bad map file] Try again! "
                }
            }

            if (btn.name == set.MAP_LOAD_BTN_CONTINUE_GAME) {
                gamePanel = LoadGameCommand(playerDeadCb).execute()
                isMapLoaded = true
                isVisible = false
            }
        }
    }

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        val container = contentPane
        container.layout = FlowLayout()
        container.add(createButton(set.MAP_LOAD_FIRST_BUTTON_NAME))
        container.add(createButton(set.MAP_LOAD_SECOND_BUTTON_NAME))

        if (checkHasSavedGame())
            container.add(createButton(set.MAP_LOAD_BTN_CONTINUE_GAME))

        setSize(set.MAP_MENU_WIDTH, set.MAP_MENU_HEIGHT)
        isResizable = false
        isVisible = true
    }

    private fun createButton(text: String): JButton {
        val action: Action = SimpleAction()
        val btn = JButton(action)
        btn.name = text
        btn.text = text
        return btn
    }

    fun getGamePanel(): GamePanel {
        return gamePanel ?: GamePanel(getMap(), playerDeadCb)
    }

    private fun checkHasSavedGame(): Boolean {
        val file = File("src/main/resources/snapshots", "snapshot")
        return file.exists()
    }
}
