package com.roguelike.enemies.player

import com.roguelike.graphics.model.Explosion
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Move
import com.roguelike.utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.ActionListener
import javax.swing.Timer
import kotlin.system.exitProcess

/** Player class.
 * x, y represents the current com.roguelike.ememies.player coordinates in pixels
 * xCoordinate, yCoordinate represents the com.roguelike.ememies.player coordinates in squares
 * */
open class Player : Character() {

    private var color = Color.BLACK
    var hp = Settings.CHARACTER_HP; private set
    private var ex: Explosion? = null

    override var xCoordinate: Int = Settings.X_START_POINT
    override var yCoordinate: Int = Settings.Y_START_POINT

    override fun draw(g: Graphics2D) {
        g.color = color
        g.fill(this)
    }

    override fun drawAttacking(g: Graphics2D) {
        ex?.r = ex?.r?.plus(2)!!
        g.color = Color.CYAN
        ex?.draw(g)
    }

    override fun getDamage(dmg: Int) {
        color = Color.ORANGE
        val t = Timer(Settings.ATTACK_DELAY, ActionListener { color = Color.BLACK })
        t.isRepeats = false
        t.start()

        hp -= dmg

        if (hp <= 0) {
            color = Color.gray
            playerDeadCallback?.let { it() }
        }
    }

    override fun attackClosestMobs(checker: MapChecker) {
        ex = Explosion(xCoordinate, yCoordinate)
        val mobs = checker.getClosestMobs()

        for (m in mobs) {
            m.getDamage(40)
        }
    }
}
