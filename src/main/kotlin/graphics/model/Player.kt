package graphics.model

import utils.Move
import utils.Settings
import java.awt.Color
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

    fun drawAttacking(g: Graphics2D) {
        val p1 = Double((xCoordinate - 1) * 10.0, (yCoordinate - 1) * 10.0, 10.0, 10.0)
        val p2 = Double((xCoordinate) * 10.0, (yCoordinate - 1) * 10.0, 10.0, 10.0)
        val p3 = Double((xCoordinate - 1) * 10.0, (yCoordinate) * 10.0, 10.0, 10.0)

        g.color = Color(111, 80, 80, 40)
        g.fill(p1)
        g.fill(p2)
        g.fill(p3)
    }

    fun attackClosestMobs(checker: MapChecker) {
        val mobs = checker.getClosestMobs()

        for (m in mobs) {
            m.getDamage(40)
        }
    }

    override fun stepLeft(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(Move.LEFT)) {
            xCoordinate--
            return true
        }
        return true
    }

    override fun stepRight(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(Move.RIGHT)) {
            xCoordinate++
            return true
        }
        return false
    }

    override fun stepUp(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(Move.UP)) {
            yCoordinate--
            return true
        }
        return false
    }

    override fun stepDown(checker: MapChecker): Boolean {
        if (checker.checkForPlayerMove(Move.DOWN)) {
            yCoordinate++
            return true
        }
        return false
    }
}
