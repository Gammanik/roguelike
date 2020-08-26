package graphics.model

import utils.Move
import utils.Settings
import java.awt.Graphics2D

/** Player class.
 * x, y represents the current player coordinates in pixels
 * xCoordinate, yCoordinate represents the player coordinates in squares
 * */
open class Player : Character() {

    override var xCoordinate: Int = Settings.X_START_POINT

    override var yCoordinate: Int = Settings.Y_START_POINT


    override fun draw(g: Graphics2D) {
        g.color = Settings.CHARACTER_COLOR
        g.fill(this)
    }

    override fun stepLeft(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(this, Move.LEFT)) {
            xCoordinate--
            return true
        }
        return true
    }

    override fun stepRight(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(this, Move.RIGHT)) {
            xCoordinate++
            return true
        }
        return false
    }

    override fun stepUp(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(this, Move.UP)) {
            yCoordinate--
            return true
        }
        return false
    }

    override fun stepDown(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(this, Move.DOWN)) {
            yCoordinate++
            return true
        }
        return false
    }
}
