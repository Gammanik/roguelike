package graphics

import java.awt.Color
import kotlin.random.Random
import kotlin.random.nextInt

class Map {

    val xSize = 100
    val ySize = 100
    val squareSize = 10;

    val rectList = mutableListOf<MapPoint>();

    companion object {
        fun colGen(): Color {
            return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        }
    }

    init {
        for (y in 0..xSize) {
            for (x in 0..ySize) {
                rectList.add(MapPoint(x, y, colGen()));
            }
        }
    }

    fun addRectangle(x: Int, y: Int, col: Color) {
        rectList.add(MapPoint(x, y , col))
    }

}