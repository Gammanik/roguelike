package graphics.model

import ememies.Mob
import graphics.GameMap
import utils.Move
import utils.Settings
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.math.absoluteValue

class MapChecker(private val map: GameMap, private val mobs: List<Mob>, private val player: Character) {

    private val directionsDeltas = hashMapOf(
            Pair(Move.LEFT, Pair(-1, 0)),
            Pair(Move.UP, Pair(0, -1)),
            Pair(Move.RIGHT, Pair(1, 0)),
            Pair(Move.DOWN, Pair(0, 1)),
            Pair(Move.NONE, Pair(0, 0))
    )

    fun check(character: GameUnit, move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val pointsList = character.getPointsCoordinates().map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return pointsList.stream().allMatch { (x, y) -> !map.isWall(x, y) && map.isMapPoint(x, y)}
    }

    fun getClosestMobs(): List<Mob> {
        return mobs.stream().filter {
            ((it.xCoordinate - player.xCoordinate).absoluteValue < Settings.ATTACK_RADIUS) and
            (((it.yCoordinate - player.yCoordinate).absoluteValue < Settings.ATTACK_RADIUS))
        }.collect(Collectors.toList())
    }

    fun checkForPlayerMove(move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val pointsList = player.getPointsCoordinates().map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return mapCheckHelper(pointsList) && mobCheckHelper(pointsList)
    }

    fun checkForMobMove(mob: GameUnit, move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val pointsList = mob.getPointsCoordinates().map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return mapCheckHelper(pointsList) && playerCheckHelper(pointsList) && mobCheckHelper(pointsList)
    }

    fun checkForConfuse(character: GameUnit) : List<MapPoint> {
        return character
                .getPointsCoordinates()
                .stream()
                .map { pair -> map.rectMap[pair]!! }
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