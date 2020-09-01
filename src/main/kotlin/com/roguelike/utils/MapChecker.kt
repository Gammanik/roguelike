package com.roguelike.utils

import com.roguelike.enemies.Mob
import com.roguelike.graphics.GameMap
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.GameUnit
import com.roguelike.graphics.model.MapPoint
import com.roguelike.items.ItemBase
import java.util.stream.Collectors
import kotlin.math.pow
import kotlin.math.sqrt

/** a special class for map positions checks */
class MapChecker(private val map: GameMap,
                 private val mobs: List<Mob>,
                 private val player: Character) {

    private val directionsDeltas = hashMapOf(
            Pair(Move.LEFT, Pair(-1, 0)),
            Pair(Move.UP, Pair(0, -1)),
            Pair(Move.RIGHT, Pair(1, 0)),
            Pair(Move.DOWN, Pair(0, 1)),
            Pair(Move.NONE, Pair(0, 0))
    )

    /** get mobs closest to the player */
    fun getClosestMobs(): List<Mob> {
        return mobs.filter { x -> getDistanceToMob(x) <= Settings.ATTACK_RADIUS}
    }

    private fun getDistanceToMob(mob: Mob): Double {
        val xDelta = player.xCoordinate + 0.5 - mob.xCoordinate
        val yDelta = player.yCoordinate + 0.5 - mob.yCoordinate
        return sqrt(xDelta.pow(2) + yDelta.pow(2))
    }

    /** check if gameUnit cab do step */
    fun checkForGameUnitMove(unit: GameUnit, move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val oldCoordinates = unit.getPointsCoordinates()
        val newCoordinates = oldCoordinates.map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return  mapCheckHelper(newCoordinates) && gameUnitCheckHelper(oldCoordinates, newCoordinates)
    }

    /** check player points for points of confuse spell */
    fun checkForConfuse(character: GameUnit) : List<MapPoint> {
        return character.getPointsCoordinates()
                .map { pair -> map.getRectMap()[pair] ?: error("impossible coordinates") }
                .filter { x -> x.col == Settings.CONFUSE_POINT_COLOR }
    }

    fun getClosestItems(lst: List<ItemBase>): List<ItemBase> {
        return lst.filter {
            player.getPointsCoordinates().any { pp ->
                pp.first == it.x && pp.second == it.y
            }
        }
    }

    private fun gameUnitCheckHelper(old: List<Pair<Int, Int>>, new: List<Pair<Int, Int>>) : Boolean {
        val gameUnitsPointsSet = mobs.stream()
                .flatMap { x -> x.getPointsCoordinates().stream() }
                .collect(Collectors.toSet())
        gameUnitsPointsSet.addAll(player.getPointsCoordinates().toSet())
        gameUnitsPointsSet.removeAll(old)
        return !new.stream()
                .anyMatch { x-> gameUnitsPointsSet.contains(x) }
    }

    fun mapCheckHelper(list: List<Pair<Int, Int>>) : Boolean {
        return !list.any{ (x, y) -> !map.isMapPoint(x, y) || map.isWall(x, y)}
    }
}
