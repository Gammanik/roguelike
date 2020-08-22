package graphics

import java.awt.Color
import kotlin.random.Random
import kotlin.random.nextInt

class Map {

    val xSize = 100
    val ySize = 100
    val squareSize = 10;
    val backgroundColor = Color.LIGHT_GRAY
    val wallColor = Color.DARK_GRAY

    val wallSet = mutableSetOf<Pair<Int, Int>>()
    val rectMap = mutableMapOf<Pair<Int, Int>, MapPoint>()
    val wallList = arrayListOf(MapRectangle(10, 20, 10, 15), MapRectangle(50, 60, 60, 70),
        MapRectangle(30, 40, 10, 20), MapRectangle(50, 60, 20, 30),
        MapRectangle(70, 80, 10, 15), MapRectangle(90, 95, 5, 15)
    )

    fun changeColor(x: Int, y: Int, col: Color) {
        rectMap[Pair(x, y)]?.col = col
    }

    companion object {

        fun colGen(): Color {
            val colors = mutableListOf<Color>(Color.RED, Color.BLACK, Color.DARK_GRAY);

//            return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

            return colors[Random.nextInt(colors.size)]
        }
    }

    init {

        for (rect in wallList) {
            for (x in rect.x1..rect.x2) {
                for (y in rect.y1..rect.y2) {
                    wallSet.add(Pair(x, y))
                }
            }
        }

        for (y in 0..xSize) {
            for (x in 0..ySize) {
                rectMap[Pair(x, y)] = MapPoint(x, y, if (wallSet.contains(Pair(x, y))) wallColor else backgroundColor)
            }
        }


    }



//    fun addRectangle(x: Int, y: Int, col: Color) {
//        rectList.add(MapPoint(x, y , col))
//    }

}