package ememies

import graphics.model.Character
import graphics.model.GameUnit
import graphics.model.MapChecker
import utils.MobSettings
import utils.Move
import utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D

data class Mob(override var xCoordinate: Int, override var yCoordinate: Int,
               val color: Color,
               var currentBehavior: BehaviourStrategy)
    : Rectangle2D.Double(xCoordinate.toDouble(), yCoordinate.toDouble(), MobSettings.MOB_SIZE, MobSettings.MOB_SIZE),
    GameUnit {

    var hp = 100; private set

    override fun draw(g: Graphics2D) {
        g.color = color
        g.fill(this)
    }

    fun getDamage(dmg: Int) {
        hp -= dmg
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


    /** mob behaviour depends on player distance and currentStrategy */
    fun behave(p: Character, checker: MapChecker) {
        currentBehavior.behave(p, this, checker)
    }

    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate))
    }
}
