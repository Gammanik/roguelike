package graphics.model

import utils.Settings
import javax.swing.text.StyledEditorKit

interface GameUnit {
    val checker: MapChecker
    fun getPointsCoordinates() : ArrayList<Pair<Int,Int>>

    var xCoordinate: Int
    var yCoordinate: Int

    fun stepLeft() : Boolean

    fun stepRight() : Boolean

    fun stepUp() : Boolean

    fun stepDown() : Boolean
}