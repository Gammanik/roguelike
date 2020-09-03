package com.roguelike

import com.roguelike.graphics.GameMap
import com.roguelike.graphics.map_loading.BadMapFileException
import com.roguelike.items.AidItem
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Test
import java.awt.Robot
import java.awt.event.KeyEvent
import java.io.File


class GameTest {
    @Test
    fun testCreateMap() {
        val m = GameMap()
        assertNotNull(m)
        assertTrue(m.getRectMap().isNotEmpty())
    }

    @Test
    fun testCreateMapFromFile() {
        val filename = "src/test/resources/mapExample"
        val m = GameMap(File(filename))
        assertNotNull(m)
        assertTrue(m.getRectMap().isNotEmpty())
    }

    @Test
    fun testBadMapFromFile() {
        val filename = "src/test/resources/badMapExample"
        assertThrows<BadMapFileException>({ GameMap(File(filename)) }, null)
    }

    @Test
    fun testInfoMenuPanelNotAffectItemNum() {
        val gamePanel = GamePanel(GameMap()) {}
        val items = listOf(AidItem(3, 3), PoisonItem(4,3), PowerUpItem(3,4))
        val player = gamePanel.player
        items.forEach { player.putItemOn(it) }

        val info = InfoMenuPanel(gamePanel)
        assertNull(player.itemNumChosen)
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
