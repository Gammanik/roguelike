package com.roguelike

import com.roguelike.graphics.GameMap
import com.roguelike.graphics.model.Player
import com.roguelike.utils.Keys
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel
import com.roguelike.utils.Settings as set

/** The main game window */
class GamePanel(private val gameMap: GameMap) : JPanel(), KeyListener {

    private val player = Player(set.X_START_POINT, set.Y_START_POINT)

    fun getPlayer(): Player {
        return player
    }

    init {
        this.addKeyListener(this)
    }

    override fun getPreferredSize() : Dimension {
        return Dimension(800, 600)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g1 = g as Graphics2D

        for (point in gameMap.getRectImmutableMap().values) {
            g1.color = point.col
            g1.fill3DRect(point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                    set.SQUARE_SIZE, set.SQUARE_SIZE, true)
        }

        g1.color = set.CHARACTER_COLOR
        g.fill(player)
    }

    override fun keyTyped(p0: KeyEvent?) {}

    override fun keyPressed(p0: KeyEvent?) {
        if (p0 == null)
            return

        when (p0.keyCode) {

            Keys.KEY_UP -> {
                if (player.yCoordinate > 0 && !gameMap.isWall(player.xCoordinate, player.yCoordinate - 1)
                        && !gameMap.isWall(player.xCoordinate + 1, player.yCoordinate - 1)) {
                    player.yCoordinate -= set.VELOCITY
                }
            }

            Keys.KEY_DOWN -> {
                if (player.yCoordinate + 2 < set.Y_POINTS_COUNTS && !gameMap.isWall(player.xCoordinate, player.yCoordinate + 2)
                        && !gameMap.isWall(player.xCoordinate + 1, player.yCoordinate + 2)) {
                    player.yCoordinate += set.VELOCITY
                }
            }

            Keys.KEY_RIGHT -> {
                if (player.xCoordinate + 2 < set.X_POINTS_COUNTS && !gameMap.isWall(player.xCoordinate + 2, player.yCoordinate)
                        && !gameMap.isWall(player.xCoordinate + 2, player.yCoordinate + 1)) {
                    player.xCoordinate += set.VELOCITY
                }
            }

            Keys.KEY_LEFT -> {
                if (player.xCoordinate > 0 && !gameMap.isWall(player.xCoordinate - 1, player.yCoordinate)
                        && !gameMap.isWall(player.xCoordinate - 1, player.yCoordinate + 1)) {
                    player.xCoordinate -= set.VELOCITY
                }
            }

        }
        player.updatePosition()
        repaint()
    }

    override fun keyReleased(p0: KeyEvent?) {}
}
