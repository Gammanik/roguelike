package com.roguelike

import com.roguelike.graphics.GameMap
import com.roguelike.graphics.model.BadMapFileException
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Test
import com.roguelike.utils.Keys
import com.roguelike.utils.Settings
import java.awt.event.KeyEvent
import java.io.File

class GameTest {
    @Test
    fun testCreateMap() {
        val m = GameMap()
        assertNotNull(m)
        assertTrue(m.getRectImmutableMap().isNotEmpty())
    }

    @Test
    fun testCreateMapFromFile() {
        val filename = "src/test/resources/mapExample"
        val m = GameMap(File(filename))
        assertNotNull(m)
        assertTrue(m.getRectImmutableMap().isNotEmpty())
    }

    @Test // there is no wall at right
    fun testPlayerMovingRight() {
        val filename = "src/test/resources/mapExample"
        val game = GamePanel(GameMap(File(filename)))
        val eventMoveRight = KeyEvent(game, 0, 22, 33, Keys.KEY_RIGHT, 'd')
        game.keyPressed(eventMoveRight)

        val p = game.getPlayer()
        assertEquals(p.xCoordinate, 1)
        assertEquals(p.x, (1 * Settings.SQUARE_SIZE).toDouble())

        assertEquals(p.yCoordinate, 0)
        assertEquals(p.y, (0 * Settings.SQUARE_SIZE).toDouble())
    }

    @Test
    fun testPlayerDontMoveOutOfMap() {
        val game = GamePanel(GameMap())
        val eventMoveUp = KeyEvent(game, 0, 0, 0, Keys.KEY_UP, 'w')
        game.keyPressed(eventMoveUp)

        val eventMoveLeft = KeyEvent(game, 0, 0, 0, Keys.KEY_LEFT, 'a')
        game.keyPressed(eventMoveLeft)

        val p = game.getPlayer()
        assertEquals(p.xCoordinate, 0)
        assertEquals(p.x, (0 * Settings.SQUARE_SIZE).toDouble())

        assertEquals(p.yCoordinate, 0)
        assertEquals(p.y, (0 * Settings.SQUARE_SIZE).toDouble())
    }

    @Test
    fun testPlayerDontBreakTheWall() {
        val filename = "src/test/resources/mapWithWall"
        val game = GamePanel(GameMap(File(filename)))

        val eventMoveRight = KeyEvent(game, 0, 0, 0, Keys.KEY_RIGHT, 'a')
        game.keyPressed(eventMoveRight)
        game.keyPressed(eventMoveRight)

        var p = game.getPlayer()
        assertEquals(p.xCoordinate, 2)
        assertEquals(p.x, (2 * Settings.SQUARE_SIZE).toDouble())
        assertEquals(p.yCoordinate, 0)
        assertEquals(p.y, (0 * Settings.SQUARE_SIZE).toDouble())

        // should be stumbled at the wall
        game.keyPressed(eventMoveRight)
        p = game.getPlayer()
        assertEquals(p.xCoordinate, 2)
        assertEquals(p.x, (2 * Settings.SQUARE_SIZE).toDouble())
        assertEquals(p.yCoordinate, 0)
        assertEquals(p.y, (0 * Settings.SQUARE_SIZE).toDouble())

        val eventMoveDown = KeyEvent(game, 0, 0, 0, Keys.KEY_DOWN, 'a')
        game.keyPressed(eventMoveDown)
        assertEquals(p.xCoordinate, 2)
        assertEquals(p.x, (2 * Settings.SQUARE_SIZE).toDouble())
        assertEquals(p.yCoordinate, 0)
        assertEquals(p.y, (0 * Settings.SQUARE_SIZE).toDouble())
    }

    @Test
    fun testBadMapFromFile() {
        val filename = "src/test/resources/badMapExample"
        assertThrows<BadMapFileException>({ GameMap(File(filename)) }, null)
    }

    private inline fun <reified T : Exception> assertThrows(runnable: () -> Any?, message: String?) {
        try {
            runnable.invoke()
        } catch (e: Throwable) {
            if (e is T) {
                message?.let {
                    Assert.assertEquals(it, "${e.message}")
                }
                return
            }
            Assert.fail("expected ${T::class.qualifiedName} but caught " +
                    "${e::class.qualifiedName} instead")
        }
        Assert.fail("expected ${T::class.qualifiedName}")
    }
}
