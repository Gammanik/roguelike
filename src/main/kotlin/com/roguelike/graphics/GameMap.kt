package com.roguelike.graphics

import com.roguelike.graphics.map_loading.BadMapFileException
import com.roguelike.graphics.model.MapPoint
import com.roguelike.graphics.model.MapRectangle
import com.roguelike.utils.Settings
import java.io.File


/** Class representing the game field
 * each element on map characterized by it's x,y coordinates*/
class GameMap {
    var wallSet = mutableSetOf<Pair<Int, Int>>(); private set
    private var rectMap = mutableMapOf<Pair<Int, Int>, MapPoint>()

    /** getter for rectMap */
    fun getRectMap(): Map<Pair<Int, Int>, MapPoint> {
        return rectMap
    }

    constructor(wallSet: MutableSet<Pair<Int, Int>>, rectMap: MutableMap<Pair<Int, Int>, MapPoint>) {
        this.wallSet = wallSet
        this.rectMap = rectMap
    }

    private var wallList = listOf(
        MapRectangle(0, 1, 2, 3),
        MapRectangle(-1, -2, -3, -4)
    ) // Settings.WALL_LIST

    private fun generateRandomWalls() {
//        wallList.shuffle()
//        wallList = wallList.subList(0, (wallList.size * Settings.WALL_SUBSET_SIZE).toInt())
    }

    constructor() {
        generateRandomWalls()

        for (rect in wallList) { // todo: cancel
            for (x in rect.x1..rect.x2) {
                for (y in rect.y1..rect.y2) {
                    wallSet.add(Pair(x, y))
                }
            }
        }

        for (x in 0 until 3) { // todo: Settings.X_POINTS_COUNTS
            for (y in 0 until 3) { // todo: Settings.Y_POINTS_COUNTS
                val pointColor = if (wallSet.contains(Pair(x, y))) Settings.WALL_COLOR else Settings.BACKGROUND_COLOR
                rectMap[Pair(x, y)] = MapPoint(x, y, pointColor)
            }
        }

        addConfusePoint(5, 5)
    }

    /** add a confuse point */
    fun addConfusePoint(xCoord: Int, yCoord: Int) {
        rectMap[Pair(xCoord, yCoord)] = MapPoint(xCoord, yCoord, Settings.CONFUSE_POINT_COLOR)
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
                    else -> { throw BadMapFileException()
                    }
                }

            }
        }

    }

    /** check if (x, y) is a map point */
    fun isMapPoint(x: Int, y: Int) : Boolean {
        return 0 <= x && x < Settings.X_POINTS_COUNTS
                && 0 <= y && y < Settings.Y_POINTS_COUNTS
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
