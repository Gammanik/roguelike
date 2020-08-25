package graphics.model

import utils.Settings
import java.awt.geom.Ellipse2D

abstract class Character(override val checker: MapChecker) : Ellipse2D.Double(0.0, 0.0,
        Settings.CHARACTER_DIAMETER.toDouble(), Settings.CHARACTER_DIAMETER.toDouble()),
        GameUnit {

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate), Pair(xCoordinate + 1, yCoordinate),
                    Pair(xCoordinate, yCoordinate + 1), Pair(xCoordinate + 1, yCoordinate + 1))
    }

    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }
}