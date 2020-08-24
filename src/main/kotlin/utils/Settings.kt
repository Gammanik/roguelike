package utils

import java.awt.Color

object Settings {
    const val WIDTH = 800
    const val HEIGHT = 600
    const val FRAME_WIDTH = 800
    const val FRAME_HEIGHT = 700
    const val SQUARE_SIZE = 10
    const val VELOCITY = 1
    const val CHARACTER_DIAMETER = 20
    const val X_POINTS_COUNTS = 80
    const val Y_POINTS_COUNTS = 60
    const val X_START_POINT = 0
    const val Y_START_POINT = 0
    const val BACKGROUND_SYMBOL = '.'
    const val WALL_SYMBOL = '#'
    const val LOAD_MAP_UPDATE_PERIOD = 250L
    val CHARACTER_COLOR: Color = Color.BLACK
    val BACKGROUND_COLOR: Color = Color.WHITE
    val WALL_COLOR: Color = Color.DARK_GRAY
    const val MAP_LOAD_FIRST_BUTTON_NAME = "Random map button"
    const val MAP_LOAD_SECOND_BUTTON_NAME = "Load from file"
}