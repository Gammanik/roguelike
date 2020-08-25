package utils

import graphics.model.MapRectangle
import java.awt.Color

/** game settings */
object Settings {
    const val DELAY = 50

    const val WIDTH = 800
    const val HEIGHT = 600
    const val MAP_MENU_WIDTH = 400
    const val MAP_MENU_HEIGHT = 100
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
    val CONFUSE_POINT_COLOR: Color = Color.PINK

    const val MAP_LOAD_FIRST_BUTTON_NAME = "Random map"
    const val MAP_LOAD_SECOND_BUTTON_NAME = "Load from file"

    const val WALL_SUBSET_SIZE = 0.8
    val WALL_LIST = mutableListOf(
            MapRectangle(3, 6, 7, 11),
            MapRectangle(0, 2, 14, 16),
            MapRectangle(3, 6, 18, 22),
            MapRectangle(2, 7, 26, 28),
            MapRectangle(2, 7, 32, 34),
            MapRectangle(3, 7, 38, 41),
            MapRectangle(0, 4, 44, 47),
            MapRectangle(0, 5, 50, 53),
            MapRectangle(0, 5, 57, 61),
            MapRectangle(8, 13, 1, 6),
            MapRectangle(11, 14, 8, 12),
            MapRectangle(11, 16, 14, 17),
            MapRectangle(10, 15, 20, 23),
            MapRectangle(9, 13, 27, 32),
            MapRectangle(11, 13, 32, 35),
            MapRectangle(8, 11, 36, 39),
            MapRectangle(8, 10, 42, 44),
            MapRectangle(10, 12, 48, 52),
            MapRectangle(8, 13, 56, 58),
            MapRectangle(19, 24, 1, 4),
            MapRectangle(19, 22, 7, 11),
            MapRectangle(19, 24, 14, 17),
            MapRectangle(19, 24, 18, 23),
            MapRectangle(19, 23, 24, 29),
            MapRectangle(17, 22, 30, 32),
            MapRectangle(19, 22, 37, 42),
            MapRectangle(17, 21, 45, 49),
            MapRectangle(18, 21, 49, 51),
            MapRectangle(17, 22, 56, 59),
            MapRectangle(24, 28, 3, 6),
            MapRectangle(25, 29, 8, 10),
            MapRectangle(25, 28, 13, 16),
            MapRectangle(27, 31, 21, 26),
            MapRectangle(26, 29, 24, 26),
            MapRectangle(25, 28, 33, 35),
            MapRectangle(26, 30, 37, 42),
            MapRectangle(25, 29, 45, 47),
            MapRectangle(26, 28, 51, 53),
            MapRectangle(26, 30, 56, 58),
            MapRectangle(35, 38, 3, 7),
            MapRectangle(32, 34, 7, 12),
            MapRectangle(32, 37, 14, 19),
            MapRectangle(34, 39, 19, 21),
            MapRectangle(35, 40, 25, 28),
            MapRectangle(33, 35, 33, 36),
            MapRectangle(35, 37, 39, 42),
            MapRectangle(35, 39, 43, 48),
            MapRectangle(33, 37, 51, 56),
            MapRectangle(35, 37, 55, 60),
            MapRectangle(41, 44, 2, 6),
            MapRectangle(43, 47, 9, 14),
            MapRectangle(41, 44, 13, 17),
            MapRectangle(43, 46, 19, 24),
            MapRectangle(41, 45, 25, 29),
            MapRectangle(40, 44, 32, 35),
            MapRectangle(42, 47, 36, 38),
            MapRectangle(41, 46, 43, 46),
            MapRectangle(43, 46, 48, 52),
            MapRectangle(41, 46, 57, 62),
            MapRectangle(51, 55, 3, 7),
            MapRectangle(49, 53, 9, 14),
            MapRectangle(49, 52, 13, 18),
            MapRectangle(48, 52, 18, 21),
            MapRectangle(50, 53, 27, 31),
            MapRectangle(49, 51, 33, 35),
            MapRectangle(49, 52, 39, 43),
            MapRectangle(51, 53, 44, 46),
            MapRectangle(50, 52, 49, 52),
            MapRectangle(48, 50, 54, 59),
            MapRectangle(56, 60, 0, 3),
            MapRectangle(56, 58, 6, 8),
            MapRectangle(59, 61, 15, 19),
            MapRectangle(58, 62, 21, 25),
            MapRectangle(57, 60, 25, 30),
            MapRectangle(57, 61, 31, 33),
            MapRectangle(58, 61, 39, 42),
            MapRectangle(59, 62, 45, 49),
            MapRectangle(56, 61, 49, 53),
            MapRectangle(57, 61, 57, 60),
            MapRectangle(67, 69, 2, 6),
            MapRectangle(67, 72, 6, 8),
            MapRectangle(66, 71, 14, 19),
            MapRectangle(67, 71, 20, 23),
            MapRectangle(65, 68, 27, 31),
            MapRectangle(67, 70, 31, 33),
            MapRectangle(67, 70, 37, 42),
            MapRectangle(67, 70, 42, 46),
            MapRectangle(65, 67, 48, 50),
            MapRectangle(67, 72, 57, 61),
            MapRectangle(74, 77, 0, 4),
            MapRectangle(72, 77, 9, 14),
            MapRectangle(75, 80, 14, 19),
            MapRectangle(73, 77, 18, 20),
            MapRectangle(74, 79, 27, 31),
            MapRectangle(74, 78, 30, 32),
            MapRectangle(72, 74, 39, 43),
            MapRectangle(72, 75, 45, 50),
            MapRectangle(75, 77, 50, 54),
            MapRectangle(73, 77, 54, 59)
    )

}