package com.roguelike.utils

import com.roguelike.ememies.Mob
import com.roguelike.graphics.GameMap
import com.roguelike.ememies.player.Character
import com.roguelike.ememies.GameUnit
import com.roguelike.graphics.model.MapPoint
import java.util.stream.Collectors
import kotlin.math.pow
import kotlin.math.sqrt

/** a special class for map positions checks */
class MapChecker(private val map: GameMap, private val mobs: List<Mob>, private val player: Character) {

    private val directionsDeltas = hashMapOf(
            Pair(Move.LEFT, Pair(-1, 0)),
            Pair(Move.UP, Pair(0, -1)),
            Pair(Move.RIGHT, Pair(1, 0)),
            Pair(Move.DOWN, Pair(0, 1)),
            Pair(Move.NONE, Pair(0, 0))
    )

    /** get mobs closest to the player */
    fun getClosestMobs(): List<Mob> {
        return mobs.stream()
                .filter { x -> getDistanceToMob(x) <= Settings.ATTACK_RADIUS}
                .collect(Collectors.toList())
    }

    private fun getDistanceToMob(mob: Mob): Double {
        val xDelta = player.xCoordinate + 0.5 - mob.xCoordinate
        val yDelta = player.yCoordinate + 0.5 - mob.yCoordinate
        return sqrt(xDelta.pow(2) + yDelta.pow(2))
    }

    /** check if player cab do move step */
    fun checkForPlayerMove(move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val pointsList = player.getPointsCoordinates().map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return mapCheckHelper(pointsList) && mobCheckHelper(pointsList)
    }

    /** check if mob can do move step */
    fun checkForMobMove(mob: GameUnit, move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val pointsList = mob.getPointsCoordinates().map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return mapCheckHelper(pointsList) && playerCheckHelper(pointsList) && mobCheckHelper(pointsList)
    }

    /** check player points for points of confuse spell */
    fun checkForConfuse(character: GameUnit) : List<MapPoint> {
        return character
                .getPointsCoordinates()
                .stream()
                .map { pair -> map.getRectMap()[pair] ?: error("impossible coordinates") }
                .filter { x -> x.col == Settings.CONFUSE_POINT_COLOR }
                .collect(Collectors.toList())
    }

    private fun mapCheckHelper(list: List<Pair<Int, Int>>) : Boolean {
        return !list.stream()
                .anyMatch{ (x, y) -> !map.isMapPoint(x, y) || map.isWall(x, y)}
    }

    private fun playerCheckHelper(list: List<Pair<Int, Int>>) : Boolean {
        return !list.stream()
                .anyMatch{ point -> player.getPointsCoordinates().contains(point) }
    }

    private fun mobCheckHelper(list: List<Pair<Int, Int>>) : Boolean {
        return !list.stream()
                .anyMatch{ point -> mobs.flatMap{ x -> x.getPointsCoordinates() }.contains(point) }
    }

}
