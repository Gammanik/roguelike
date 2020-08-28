package com.roguelike

import com.roguelike.graphics.GameMap
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import com.roguelike.utils.Settings
import java.io.File

class GameMapTest {

    @Test
    fun testIsWall() {
        val map = GameMap(File("src/test/resources/mapWithWall"))
        assertFalse(map.isWall(0, 0))
        assertTrue(map.isWall(0, 2))
        assertFalse(map.isWall(-1, 2))
        assertFalse(map.isWall(-1, -1))
        assertFalse(map.isWall(Settings.X_POINTS_COUNTS, 0))
        assertFalse(map.isWall(0, Settings.Y_POINTS_COUNTS))
    }

    @Test
    fun testIsMapPoint() {
        val map = GameMap(File("src/test/resources/mapWithWall"))
        assertTrue(map.isMapPoint(0, 0))
        assertTrue(map.isMapPoint(0, 2))
        assertFalse(map.isMapPoint(-1, 2))
        assertFalse(map.isMapPoint(-1, -1))
        assertFalse(map.isMapPoint(Settings.X_POINTS_COUNTS, 0))
        assertFalse(map.isMapPoint(0, Settings.Y_POINTS_COUNTS))
    }
}
