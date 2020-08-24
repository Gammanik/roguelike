package graphics

import utils.Settings
import java.awt.geom.Ellipse2D


class Person(var xCoordinate: kotlin.Int, var yCoordinate: kotlin.Int)
    : Ellipse2D.Double(xCoordinate.toDouble(), yCoordinate.toDouble(), Settings.CHARACTER_DIAMETER.toDouble(), Settings.CHARACTER_DIAMETER.toDouble()) {

    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }
}
