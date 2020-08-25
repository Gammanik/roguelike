package graphics.model

import utils.Move
import utils.Settings

/** Player class.
 * x, y represents the current player coordinates in pixels
 * xCoordinate, yCoordinate represents the player coordinates in squares
 * */
open class Player(checker: MapChecker) : Character(checker) {

    override var xCoordinate: Int = Settings.X_START_POINT

    override var yCoordinate: Int = Settings.Y_START_POINT

    override fun stepLeft(): Boolean {
        if (checker.check(this, Move.LEFT)) {
            xCoordinate--
            return true
        }
        return true
    }

    override fun stepRight(): Boolean {
        if (checker.check(this, Move.RIGHT)) {
            xCoordinate++
            return true
        }
        return false
    }

    override fun stepUp(): Boolean {
        if (checker.check(this, Move.UP)) {
            yCoordinate--
            return true
        }
        return false
    }

    override fun stepDown(): Boolean {
        if (checker.check(this, Move.DOWN)) {
            yCoordinate++
            return true
        }
        return false
    }
}
