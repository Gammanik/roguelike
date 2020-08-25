package graphics.model

import graphics.GameMap
import utils.Move

class MapChecker(private val map: GameMap) {

    private val directionsDeltas = hashMapOf(
            Pair(Move.LEFT, Pair(-1, 0)),
            Pair(Move.UP, Pair(0, -1)),
            Pair(Move.RIGHT, Pair(1, 0)),
            Pair(Move.DOWN, Pair(0, 1)),
            Pair(Move.NONE, Pair(0, 0))
    )

    fun check(character: Character, move: Move): Boolean {
        val (deltaX, deltaY) = directionsDeltas[move]!!
        val pointsList = character.getPointsCoordinates().map { (x, y) -> Pair(x + deltaX, y + deltaY) }
        return pointsList.stream().allMatch { x -> !map.isWall(x.first, x.second)}
    }

}