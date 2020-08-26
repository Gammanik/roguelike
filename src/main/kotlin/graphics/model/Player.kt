package graphics.model

import ememies.Explosion
import utils.Move
import utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.ActionListener
import javax.swing.Timer
import kotlin.system.exitProcess

/** Player class.
 * x, y represents the current player coordinates in pixels
 * xCoordinate, yCoordinate represents the player coordinates in squares
 * */
open class Player : Character() {

    private var color = Color.BLACK

    var hp = 100

    override var xCoordinate: Int = Settings.X_START_POINT
    override var yCoordinate: Int = Settings.Y_START_POINT

    override fun draw(g: Graphics2D) {
        g.color = color
        g.fill(this)
    }

    var ex: Explosion? = null

    fun drawAttacking(g: Graphics2D) {
        g.color = Color.CYAN
        ex?.draw(g)
    }

    fun update() {
        ex?.r = ex?.r?.plus(2)!!
    }

    fun getDamage(dmg: Int) {
        color = Color.ORANGE
        val t = Timer(Settings.ATTACK_DELAY, ActionListener { color = Color.BLACK })
        t.isRepeats = false
        t.start()

        hp -= dmg
        println("got dmg: $hp : $this")

        if (hp <= 0) {
            color = Color.gray
            exitProcess(0)
        }
    }

    fun attackClosestMobs(checker: MapChecker) {
        ex = Explosion(xCoordinate, yCoordinate)
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
