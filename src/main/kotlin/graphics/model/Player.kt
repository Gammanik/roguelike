package graphics.model

import utils.Settings
import java.awt.geom.Ellipse2D

/** Player class.
 * x, y represents the current player coordinates in pixels
 * xCoordinate, yCoordinate represents the player coordinates in squares
 * */
open class Player(checker: MapChecker) : Character(checker) {
    override fun stepLeft() {
        xCoordinate--
    }

    override fun stepRight() {
        xCoordinate++
    }

    override fun stepUp() {
        yCoordinate--
    }

    override fun stepDown() {
        yCoordinate++
    }
}
