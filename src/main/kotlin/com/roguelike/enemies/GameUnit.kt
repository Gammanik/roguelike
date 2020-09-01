package com.roguelike.enemies

import com.roguelike.utils.MapChecker
import com.roguelike.utils.Move
import java.awt.Graphics2D

/** interface for moving units */
interface GameUnit {

    /** get coordinates array of gameunit points */
    fun getPointsCoordinates() : ArrayList<Pair<Int,Int>>

    var xCoordinate: Int
    var yCoordinate: Int

    /** draw gameunit on GamePanel */
    fun draw(g: Graphics2D) {}

    /** unit makes step. Returns true if the step is made
     * and false if not **/
    fun stepLeft(checker: MapChecker): Boolean {
        if (checker.checkForGameUnitMove(this, Move.LEFT)) {
            xCoordinate--
            return true
        }
        return false
    }

    /** unit makes step. Returns true if the step is made
     * and false if not **/
    fun stepRight(checker: MapChecker): Boolean {
        if (checker.checkForGameUnitMove(this, Move.RIGHT)) {
            xCoordinate++
            return true
        }
        return false
    }

    /** unit makes step. Returns true if the step is made
     * and false if not **/
    fun stepUp(checker: MapChecker): Boolean {
        if (checker.checkForGameUnitMove(this, Move.UP)) {
            yCoordinate--
            return true
        }
        return false
    }

    /** unit makes step. Returns true if the step is made
     * and false if not **/
    fun stepDown(checker: MapChecker): Boolean {
        if (checker.checkForGameUnitMove(this, Move.DOWN)) {
            yCoordinate++
            return true
        }
        return false
    }
}
