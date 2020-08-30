package com.roguelike.enemies.player

import com.roguelike.enemies.GameUnit
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import kotlin.math.pow
import kotlin.math.sqrt

/** abstract class for main character */
abstract class Character : Ellipse2D.Double(0.0, 0.0,
        Settings.CHARACTER_DIAMETER, Settings.CHARACTER_DIAMETER),
    GameUnit {

    var hp = Settings.CHARACTER_HP; protected set

    var playerDeadCallback: (() -> Unit)? = null
    fun addDeadCallback(cb: (() -> Unit)) {
        playerDeadCallback = cb
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate), Pair(xCoordinate + 1, yCoordinate),
                    Pair(xCoordinate, yCoordinate + 1), Pair(xCoordinate + 1, yCoordinate + 1))
    }

    /** get distance to the (x, y) point) */
    fun getDistance(x: Int, y: Int): kotlin.Double {
        return sqrt( (x - xCoordinate).toDouble().pow(2)  + (y - yCoordinate).toDouble().pow(2))
    }

    /** update mob position as a Ellipse2D */
    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    /** draw player in attack */
    open fun drawAttacking(g: Graphics2D) {}

    /** attack closest to the player mobs */
    open fun attackClosestMobs(checker: MapChecker) {}

    /** get damage from mobs */
    open fun getDamage(dmg: Int) {}
}
