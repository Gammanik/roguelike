package com.roguelike

import com.roguelike.ememies.Mob
import com.roguelike.ememies.behaviour.PassiveStrategy
import com.roguelike.graphics.GameMap
import com.roguelike.graphics.model.MapPoint
import junit.framework.TestCase.*
import org.junit.Test
import com.roguelike.ememies.player.Player
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Move
import com.roguelike.utils.Settings
import java.awt.Color
import java.io.File

class MapCheckerTest {

    @Test
    fun testReturnsClosestMobs() {
        val mapFilename = "src/test/resources/mapExample"
        val gameMap = GameMap(File(mapFilename))
        val mobs = listOf(
            Mob(1, 2, Color.CYAN, PassiveStrategy()),
            Mob(0, 2, Color.CYAN, PassiveStrategy()),
            Mob(5, 2, Color.CYAN, PassiveStrategy()))
        val player = Player()

        val checker = MapChecker(gameMap, mobs, player)

        val res = checker.getClosestMobs()
        assertEquals(mobs.size - 1, res.size)
        assertEquals(mobs.first(), res.first())
        assertEquals(mobs[1], res[1])
    }

    @Test
    fun testCheckPlayerCanMove() {
        val mapFilename = "src/test/resources/mapExample"
        val gameMap = GameMap(File(mapFilename))
        val mobs = emptyList<Mob>()
        val player = Player()

        val checker = MapChecker(gameMap, mobs, player)
        // com.roguelike.ememies.player is generated at (0,0) coordinates
        assertFalse(checker.checkForPlayerMove(Move.UP))
        assertFalse(checker.checkForPlayerMove(Move.LEFT))
        assertTrue(checker.checkForPlayerMove(Move.RIGHT))
        assertTrue(checker.checkForPlayerMove(Move.DOWN))
    }

    @Test
    fun testPlayerNotMoveToWall() {
        val mapFilename = "src/test/resources/mapWithWall"
        val gameMap = GameMap(File(mapFilename))
        val mobs = emptyList<Mob>()
        val player = Player()

        val checker = MapChecker(gameMap, mobs, player)
        // com.roguelike.ememies.player is generated at (0,0) coordinates
        assertFalse(checker.checkForPlayerMove(Move.UP))
        assertFalse(checker.checkForPlayerMove(Move.LEFT))
        assertFalse(checker.checkForPlayerMove(Move.RIGHT))
        assertFalse(checker.checkForPlayerMove(Move.DOWN))
    }

     @Test
     fun testCheckForConfuse() {
         val mapFilename = "src/test/resources/mapExample"
         val gameMap = GameMap(File(mapFilename))
         gameMap.addConfusePoint(0, 0)

         val mobs = emptyList<Mob>()
         val player = Player()

         val checker = MapChecker(gameMap, mobs, player)
         val res = checker.checkForConfuse(player)
         assertTrue(res.isNotEmpty())
         assertEquals(1, res.size)

         val confusePoint = MapPoint(0, 0, Settings.CONFUSE_POINT_COLOR)
         assertEquals(res.first(), confusePoint)
     }

    @Test
    fun testCheckMobNotMoveWall() {
        val mapFilename = "src/test/resources/mapWithWall"
        val gameMap = GameMap(File(mapFilename))
        val player = Player()

        val mobs = listOf(Mob(3, 0, Color.CYAN, PassiveStrategy()))
        val myMob = mobs.first()

        val checker = MapChecker(gameMap, mobs, player)
        assertFalse(checker.checkForMobMove(myMob, Move.UP))
        assertFalse(checker.checkForMobMove(myMob, Move.LEFT)) // wall
        assertTrue(checker.checkForMobMove(myMob, Move.DOWN))
        assertTrue(checker.checkForMobMove(myMob, Move.RIGHT))
    }

    @Test
    fun testCheckMobNotMoveToAnotherMob() {
        val mapFilename = "src/test/resources/mapWithWall"
        val gameMap = GameMap(File(mapFilename))
        val player = Player()

        val mobs = listOf(
            Mob(3, 0, Color.CYAN, PassiveStrategy()),
            Mob(4, 0, Color.CYAN, PassiveStrategy()))
        val myMob = mobs.first()

        val checker = MapChecker(gameMap, mobs, player)
        assertFalse(checker.checkForMobMove(myMob, Move.UP))
        assertFalse(checker.checkForMobMove(myMob, Move.LEFT)) // wall
        assertTrue(checker.checkForMobMove(myMob, Move.DOWN))
        assertFalse(checker.checkForMobMove(myMob, Move.RIGHT)) // anotherMob
    }
}
