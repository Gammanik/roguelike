package com.roguelike.ememies

import com.roguelike.ememies.behaviour.BehaviourStrategy
import com.roguelike.ememies.player.Character
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Move
import com.roguelike.utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.ActionListener
import java.awt.geom.Rectangle2D
import javax.swing.Timer

/** class for mob */
data class Mob(override var xCoordinate: Int, override var yCoordinate: Int,
               private var color: Color, var currentBehavior: BehaviourStrategy)
    : Rectangle2D.Double(xCoordinate.toDouble(), yCoordinate.toDouble(), Settings.MOB_SIZE, Settings.MOB_SIZE),
    GameUnit {

    var hp = Settings.MOB_HP; private set

    override fun draw(g: Graphics2D) {
        g.color = color
        g.fill(this)
    }

    /** get damage from player */
    fun getDamage(dmg: Int) {
        color = Settings.MOB_GOT_DAMAGE_COLOR
        val t = Timer(Settings.ATTACK_DELAY, ActionListener { color = Color.gray })
        t.isRepeats = false
        t.start()

        hp -= dmg
    }

    /** attack a main character */
    fun attackPlayer(player: Character) {
        player.getDamage(Settings.MOB_DAMAGE)
    }

    override fun stepLeft(checker: MapChecker): Boolean {
        if (checker.checkForMobMove(this, Move.LEFT)) {
            xCoordinate--
            return true
        }
        return false
    }

    override fun stepRight(checker: MapChecker): Boolean {
        if (checker.checkForMobMove(this, Move.RIGHT)) {
            xCoordinate++
            return true
        }
        return false
    }

    override fun stepUp(checker: MapChecker): Boolean {
        if (checker.checkForMobMove(this, Move.UP)) {
            yCoordinate--
            return true
        }
        return false
    }

    override fun stepDown(checker: MapChecker): Boolean {
        if (checker.checkForMobMove(this, Move.DOWN)) {
            yCoordinate++
            return true
        }
        return false
    }


    /** returns true if com.roguelike.ememies.player is dead for it to be deleted */
    fun behave(p: Character, checker: MapChecker): Boolean {
        currentBehavior.behave(p, this, checker)
        return hp <= 0
    }

    /** update mob position as a Rectangle2D */
    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate))
    }
}
