package graphics.model

import utils.Settings
import java.awt.geom.Ellipse2D
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Character() : Ellipse2D.Double(0.0, 0.0,
        Settings.CHARACTER_DIAMETER.toDouble(), Settings.CHARACTER_DIAMETER.toDouble()),
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
}