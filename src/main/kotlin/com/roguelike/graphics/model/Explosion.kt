package com.roguelike.graphics.model

import com.roguelike.utils.Settings
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

/** class fot explosion animation of player attack */
class Explosion(private val x: Int, private val y: Int) {
    var r = 2

    /** function for draw effect */
    fun draw(g: Graphics2D, r: Int, attackPower: Int) {
        val pixelX = x * Settings.SQUARE_SIZE + 10
        val pixelY = y * Settings.SQUARE_SIZE + 10

        g.color = Color(20, (attackPower * 3).rem(255), 255)
        g.stroke = BasicStroke(3f + (r / 5))
        g.drawOval(pixelX - r, pixelY - r, 2 * r, 2 * r)
    }
}