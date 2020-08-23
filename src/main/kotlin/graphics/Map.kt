package graphics

import graphics.model.MapPoint
import graphics.model.MapRectangle
import utils.Settings
import java.awt.Color
import java.io.File
import java.lang.Exception

class Map {

    private val xSize = Settings.X_POINTS_COUNTS
    private val ySize = Settings.Y_POINTS_COUNTS

    var wallSet = mutableSetOf<Pair<Int, Int>>()
    val rectMap = mutableMapOf<Pair<Int, Int>, MapPoint>()

    private val wallList = arrayListOf(rectGen(3, 2, 4, 2), rectGen(2, 7, 2, 4),
        rectGen(10, 10, 3, 3), rectGen(15, 7, 2, 4),
        rectGen(31, 20, 3, 5), rectGen(2, 7, 2, 4),
        rectGen(5, 33, 5, 3), rectGen(27, 5, 2, 4)
    )

    constructor() {

        for (rect in wallList) {
            for (x in rect.x1..rect.x2) {
                for (y in rect.y1..rect.y2) {
                    wallSet.add(Pair(x, y))
                }
            }
        }

        for (x in 0 until xSize) {
            for (y in 0 until ySize) {
                val pointColor = if (wallSet.contains(Pair(x, y))) Settings.WALL_COLOR else Settings.BACKGROUND_COLOR
                rectMap[Pair(x, y)] = MapPoint(x, y, pointColor)
            }
        }

    }

    constructor(file: File) {

        var charSequence : CharArray
        var symbol: Char

        for ((lineNumber, line) in file.readLines().withIndex())  {

            charSequence = line.toCharArray()

            for (x in 0 until Settings.Y_POINTS_COUNTS) {
                symbol =  charSequence[x]

                if (symbol.equals(Settings.BACKGROUND_SYMBOL)) {
                    rectMap[Pair(x, lineNumber)] = MapPoint(x, lineNumber, Settings.BACKGROUND_COLOR)
                } else if (symbol.equals(Settings.WALL_COLOR)) {
                    rectMap[Pair(x, lineNumber)] = MapPoint(x, lineNumber, Settings.WALL_COLOR)
                    wallSet.add(Pair(x, lineNumber))
                } else {
                    throw BadMapFileException()
                }

            }

        }
    }

    fun changeColor(x: Int, y: Int, col: Color) {
        rectMap[Pair(x, y)]?.col = col
    }

    fun isWall(x: Int, y: Int): Boolean {
        val point = rectMap[Pair(x, y)]
        if (point != null) {
            if (point.col == Settings.WALL_COLOR) {
                return true
            }
        }
        return false
    }

    private fun rectGen(x: Int, y: Int, w: Int, h: Int): MapRectangle {
        return MapRectangle(x, x + w, y , y + h)
    }
}
