package graphics.model

import graphics.GameMap
import utils.Move
import utils.Settings
import java.util.stream.Collectors

class MapChecker(private val map: GameMap) {

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

    fun checkForConfuse(character: GameUnit) : List<MapPoint> {
        return character
                .getPointsCoordinates()
                .stream()
                .map { pair -> map.rectMap[pair]!! }
                .filter { x -> x.col == Settings.CONFUSE_POINT_COLOR }
                .collect(Collectors.toList())
    }

}