package ememies

import graphics.model.Character
import graphics.model.GameUnit
import graphics.model.MapChecker
import graphics.model.Player
import utils.MobSettings
import utils.Settings
import java.awt.Color
import java.awt.geom.Rectangle2D

data class Mob(var currX: Int, var currY: Int,
               private var hp: Int,
               val color: Color,
               var currentBehavior: BehaviourStrategy,
               override val checker: MapChecker)
    : Rectangle2D.Double(currX.toDouble(), currY.toDouble(), MobSettings.MOB_SIZE, MobSettings.MOB_SIZE),
    GameUnit {

    fun behave(p: Character) {
        currentBehavior.behave(p, this)
    }

    fun updatePosition() {
        x = currX.toDouble() * Settings.SQUARE_SIZE
        y = currY.toDouble() * Settings.SQUARE_SIZE
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(currX, currY))
    }
}
