package com.roguelike.ememies.player

import com.roguelike.ememies.GameUnit
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Settings
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Character : Ellipse2D.Double(0.0, 0.0,
        Settings.CHARACTER_DIAMETER, Settings.CHARACTER_DIAMETER),
    GameUnit {

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate), Pair(xCoordinate + 1, yCoordinate),
                    Pair(xCoordinate, yCoordinate + 1), Pair(xCoordinate + 1, yCoordinate + 1))
    }

    fun getDistance(x: Int, y: Int): kotlin.Double {
        return sqrt( (x - xCoordinate).toDouble().pow(2)  + (y - yCoordinate).toDouble().pow(2))
    }

    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    open fun drawAttacking(g: Graphics2D) {}
    open fun attackClosestMobs(checker: MapChecker) {}
    open fun getDamage(dmg: Int) {}
}