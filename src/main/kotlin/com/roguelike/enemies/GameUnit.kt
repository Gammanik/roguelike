package com.roguelike.enemies

import com.roguelike.utils.MapChecker
import java.awt.Graphics2D

/** interface for moving units */
interface GameUnit {

    /** get coordinates array of gameunit points */
    fun getPointsCoordinates() : ArrayList<Pair<Int,Int>>

    var xCoordinate: Int
    var yCoordinate: Int

    /** draw gameunit on GamePanel */
    fun draw(g: Graphics2D) {}

    /** do step left */
    fun stepLeft(checker: MapChecker) : Boolean

    /** do step right */
    fun stepRight(checker: MapChecker) : Boolean

    /** do step up */
    fun stepUp(checker: MapChecker) : Boolean

    /** do step down */
    fun stepDown(checker: MapChecker) : Boolean
}
