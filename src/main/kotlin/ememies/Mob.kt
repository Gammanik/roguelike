package ememies

import ememies.behaviour.BehaviourStrategy
import player.Character
import graphics.model.GameUnit
import utils.MapChecker
import utils.MobSettings
import utils.Move
import utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.ActionListener
import java.awt.geom.Rectangle2D
import javax.swing.Timer

data class Mob(override var xCoordinate: Int, override var yCoordinate: Int,
               var color: Color, // todo: private set
               var currentBehavior: BehaviourStrategy
)
    : Rectangle2D.Double(xCoordinate.toDouble(), yCoordinate.toDouble(), MobSettings.MOB_SIZE, MobSettings.MOB_SIZE),
    GameUnit {

    var hp = 100; private set

    override fun draw(g: Graphics2D) {
        g.color = color
        g.fill(this)
    }

    fun getDamage(dmg: Int) {
        color = Color.ORANGE
        val t = Timer(Settings.ATTACK_DELAY, ActionListener { color = Color.red })
        t.isRepeats = false
        t.start()

        hp -= dmg
        if (hp <= 0) {
            color = Color.gray
        }
    }

    fun attackPlayer(player: Character) {
        player.getDamage(1)
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


    /** returns true if player is dead for it to be deleted */
    fun behave(p: Character, checker: MapChecker): Boolean {
        currentBehavior.behave(p, this, checker)
        return hp <= 0
    }

    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate))
    }
}
