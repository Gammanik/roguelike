package graphics

import graphics.model.MapPoint
import graphics.model.MapRectangle
import java.awt.Color

class Map {

    val xSize = 80
    val ySize = 60
    val squareSize = 10;
    val backgroundColor = Color.LIGHT_GRAY
    val wallColor = Color.DARK_GRAY

    var wallSet = mutableSetOf<Pair<Int, Int>>()
    val rectMap = mutableMapOf<Pair<Int, Int>, MapPoint>()
    val wallList = arrayListOf(rectGen(3, 2, 4, 2), rectGen(2, 7, 2, 4),
        rectGen(10, 10, 3, 3), rectGen(15, 7, 2, 4),
        rectGen(31, 20, 3, 5), rectGen(2, 7, 2, 4),
        rectGen(5, 33, 5, 3), rectGen(27, 5, 2, 4)
    )

    fun changeColor(x: Int, y: Int, col: Color) {
        rectMap[Pair(x, y)]?.col = col
    }

    init {

        for (rect in wallList) {
            for (x in rect.x1..rect.x2) {
                for (y in rect.y1..rect.y2) {
                    wallSet.add(Pair(x, y))
                }
            }
        }

        for (x in 0 until xSize) {
            for (y in 0 until ySize) {
                rectMap[Pair(x, y)] = MapPoint(x, y, if (wallSet.contains(Pair(x, y))) wallColor else backgroundColor)
            }
        }
    }

    fun isWall(x: Int, y: Int): Boolean {
        val point = rectMap[Pair(x, y)]
        if (point != null) {
            if (point.col == wallColor) {
                return true
            }
        }
        return false
    }

    fun rectGen(x: Int, y: Int, w: Int, h: Int): MapRectangle {
        return MapRectangle(x, x + w, y , y + h)
    }
}
