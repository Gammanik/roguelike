package graphics.model

import utils.Move
import utils.Settings
import java.awt.geom.Ellipse2D

/** Player class.
 * x, y represents the current player coordinates in pixels
 * xCoordinate, yCoordinate represents the player coordinates in squares
 * */
open class Player(checker: MapChecker) : Character(checker) {
    override fun stepLeft() {
        if (checker.check(this, Move.LEFT)) {
            xCoordinate--
        }
    }

    override fun stepRight() {
        if (checker.check(this, Move.RIGHT)) {
            xCoordinate++
        }
    }

    override fun stepUp() {
        if (checker.check(this, Move.UP)) {
            yCoordinate--
        }
    }

    override fun stepDown() {
        if (checker.check(this, Move.DOWN)) {
            yCoordinate++
        }
    }
}
