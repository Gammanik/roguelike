package graphics.model

import graphics.GameMap

class MapChecker(private val map: GameMap) {
    fun check(character: Character): Boolean {
        val pointsList = character.getPointsCoordinates()
        return pointsList.stream().allMatch { x -> !map.isWall(x.first, x.second)}
    }
}