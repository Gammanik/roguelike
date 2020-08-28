package com.roguelike.graphics.model

import com.roguelike.utils.Settings
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

/** class fot explosion animation of player attack */
class Explosion(private val x: Int, private val y: Int) {
    var r = 0

    /** function for draw effect */
    fun draw(g: Graphics2D) {
        val pixelX = x * Settings.SQUARE_SIZE + 10
        val pixelY = y * Settings.SQUARE_SIZE + 10

        g.color = Color.CYAN
        g.stroke = BasicStroke(6f)
        g.drawOval(pixelX - r, pixelY - r, 2 * r, 2 * r)
    }
}