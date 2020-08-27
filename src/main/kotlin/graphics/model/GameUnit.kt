package graphics.model

import utils.MapChecker
import java.awt.Graphics2D

interface GameUnit {
    fun getPointsCoordinates() : ArrayList<Pair<Int,Int>>

    var xCoordinate: Int
    var yCoordinate: Int

    fun draw(g: Graphics2D) {}

    fun stepLeft(checker: MapChecker) : Boolean

    fun stepRight(checker: MapChecker) : Boolean

    fun stepUp(checker: MapChecker) : Boolean

    fun stepDown(checker: MapChecker) : Boolean
}
