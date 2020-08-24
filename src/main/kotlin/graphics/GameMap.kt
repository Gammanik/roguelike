package graphics

import graphics.model.BadMapFileException
import graphics.model.MapPoint
import utils.Settings
import java.io.File


/** Class representing the game field
 * each element on map characterized by it's x,y coordinates*/
class GameMap {
    private var wallSet = mutableSetOf<Pair<Int, Int>>()
    val rectMap = mutableMapOf<Pair<Int, Int>, MapPoint>()

    private var wallList = Settings.WALL_LIST

    private fun generateRandomWalls() {
        wallList.shuffle()
        wallList = wallList.subList(0, (wallList.size * Settings.WALL_SUBSET_SIZE).toInt())
    }

    constructor() {
        generateRandomWalls()

        for (rect in wallList) {
            for (x in rect.x1..rect.x2) {
                for (y in rect.y1..rect.y2) {
                    wallSet.add(Pair(x, y))
                }
            }
        }

        for (x in 0 until Settings.X_POINTS_COUNTS) {
            for (y in 0 until Settings.Y_POINTS_COUNTS) {
                val pointColor = if (wallSet.contains(Pair(x, y))) Settings.WALL_COLOR else Settings.BACKGROUND_COLOR
                rectMap[Pair(x, y)] = MapPoint(x, y, pointColor)
            }
        }

    }

    constructor(file: File) {
        var charSequence: CharArray
        var symbol: Char

        for ((lineNumber, line) in file.readLines().withIndex()) {
            charSequence = line.toCharArray()

            for (x in 0 until Settings.X_POINTS_COUNTS) {
                symbol = charSequence[x]

                when (symbol) {
                    Settings.BACKGROUND_SYMBOL -> {
                        rectMap[Pair(x, lineNumber)] = MapPoint(x, lineNumber, Settings.BACKGROUND_COLOR)
                    }
                    Settings.WALL_SYMBOL -> {
                        rectMap[Pair(x, lineNumber)] = MapPoint(x, lineNumber, Settings.WALL_COLOR)
                        wallSet.add(Pair(x, lineNumber))
                    }
                    else -> { throw BadMapFileException() }
                }

            }
        }

    }

    /** check if current coordinate is a wall*/
    fun isWall(x: Int, y: Int): Boolean {
        val point = rectMap[Pair(x, y)]
        if (point != null) {
            if (point.col == Settings.WALL_COLOR) {
                return true
            }
        }
        return false
    }

}
