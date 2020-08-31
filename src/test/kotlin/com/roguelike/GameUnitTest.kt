package com.roguelike

import GamePanel
import com.roguelike.enemies.Mob
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.graphics.GameMap
import junit.framework.TestCase.*
import org.junit.Test
import com.roguelike.enemies.player.Player
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Settings
import java.io.File

class GameUnitTest {
    private val passiveStrategy = PassiveStrategy()
    private val aggressiveStrategy = AggressiveStrategy()
    private val funkyStrategy = FunkyStrategy()

    @Test
    fun testMobGettingDamage() {
        val mob = Mob(0, 0, passiveStrategy)
        assertEquals(Settings.MOB_HP, mob.hp)
        mob.getDamage(1)
        assertEquals(Settings.MOB_HP - 1, mob.hp)
        mob.getDamage(Settings.MOB_HP - 1)
        assertEquals(0, mob.hp)
    }

    @Test
    fun testMobGotDead() {
        val mob = Mob(0, 0, passiveStrategy)
        val p = Player()
        val gameMap = GameMap(File("src/test/resources/mapWithWall"))
        val checker = MapChecker(gameMap, emptyList(), p)
        assertFalse(mob.behave(p, checker))

        assertEquals(Settings.MOB_HP, mob.hp)
        mob.getDamage(1)
        assertFalse(mob.behave(p, checker))

        assertEquals(Settings.MOB_HP - 1, mob.hp)
        mob.getDamage(Settings.MOB_HP - 1)
        assertEquals(0, mob.hp)
        assertTrue(mob.behave(p, checker)) // means that the mob is actually dead
    }

    @Test
    fun testMobAttackPlayer() {
        val mob = Mob(0, 0, passiveStrategy)
        val p = Player()

        assertEquals(Settings.CHARACTER_MAX_HP, p.hp)
        mob.attackPlayer(p)
        assertEquals(Settings.CHARACTER_MAX_HP - Settings.MOB_DAMAGE, p.hp)
    }

    @Test
    fun testPlayerGotDamage() {
        val p = Player()
        assertEquals(Settings.CHARACTER_MAX_HP, p.hp)
        p.getDamage(Settings.MOB_DAMAGE)
        assertEquals(Settings.CHARACTER_MAX_HP - Settings.MOB_DAMAGE, p.hp)
    }

    @Test
    fun testMobMovement() {
        val m = Mob(2, 1, passiveStrategy)
        val gameMap = GameMap(File("src/test/resources/mapWithWall"))
        val checker = MapChecker(gameMap, emptyList(), Player())

        assertTrue(m.stepDown(checker))
        assertFalse(m.stepLeft(checker))
        assertTrue(m.stepRight(checker))
        assertTrue(m.stepDown(checker))
    }

    @Test
    fun testMobAggressiveStrategy() {
        val gameMap = GameMap(File("src/test/resources/mapExample"))
        val panel = GamePanel(gameMap) {}
        val aggressiveMob = Mob(3, 0, aggressiveStrategy)
        panel.addMob(aggressiveMob)
        panel.actionPerformed(null)
        assertEquals(2, aggressiveMob.xCoordinate)
        assertEquals(0, aggressiveMob.yCoordinate)
    }

    @Test
    fun testMobFunkyStrategy() {
        val gameMap = GameMap(File("src/test/resources/mapExample"))
        val panel = GamePanel(gameMap) {}

        val funkyMob = Mob(0, 4, funkyStrategy)
        panel.addMob(funkyMob)
        panel.actionPerformed(null)

        assertEquals(0, funkyMob.xCoordinate)
        assertEquals(5, funkyMob.yCoordinate)
    }

    @Test
    fun testMobPassiveStrategy() {
        val gameMap = GameMap(File("src/test/resources/mapExample"))
        val panel = GamePanel(gameMap) {}

        val passiveMob = Mob(3, 0, passiveStrategy)
        panel.addMob(passiveMob)
        panel.actionPerformed(null)
        assertEquals(3, passiveMob.xCoordinate)
        assertEquals(0, passiveMob.yCoordinate)
        panel.actionPerformed(null)
        assertEquals(3, passiveMob.xCoordinate)
        assertEquals(0, passiveMob.yCoordinate)
    }

}
