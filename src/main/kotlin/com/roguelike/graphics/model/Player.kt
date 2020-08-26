package com.roguelike.graphics.model

import com.roguelike.utils.Settings
import java.awt.geom.Ellipse2D

/** Player class.
 * x, y represents the current player coordinates in pixels
 * xCoordinate, yCoordinate represents the player coordinates in squares
 * */
class Player(var xCoordinate: Int, var yCoordinate: Int)
    : Ellipse2D.Double(xCoordinate.toDouble(), yCoordinate.toDouble(), Settings.CHARACTER_DIAMETER.toDouble(), Settings.CHARACTER_DIAMETER.toDouble()) {

    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }
}
