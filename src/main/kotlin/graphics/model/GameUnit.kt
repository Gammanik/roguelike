package graphics.model

import utils.Settings
import javax.swing.text.StyledEditorKit

interface GameUnit {
    fun getPointsCoordinates() : ArrayList<Pair<Int,Int>>

    var xCoordinate: Int
    var yCoordinate: Int

    fun stepLeft(checker: MapChecker) : Boolean

    fun stepRight(checker: MapChecker) : Boolean

    fun stepUp(checker: MapChecker) : Boolean

    fun stepDown(checker: MapChecker) : Boolean
}