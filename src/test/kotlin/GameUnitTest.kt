import ememies.Mob
import ememies.behaviour.PassiveStrategy
import graphics.GameMap
import junit.framework.TestCase.*
import org.junit.Test
import player.Player
import utils.MapChecker
import utils.Settings
import java.io.File

class GameUnitTest {
    @Test
    fun testMobGettingDamage() {
        val mob = Mob(0, 0, Settings.CHARACTER_COLOR, PassiveStrategy())
        assertEquals(Settings.MOB_HP, mob.hp)
        mob.getDamage(1)
        assertEquals(Settings.MOB_HP - 1, mob.hp)
        mob.getDamage(Settings.MOB_HP - 1)
        assertEquals(0, mob.hp)
    }

    @Test
    fun testMobGotDead() {
        val mob = Mob(0, 0, Settings.CHARACTER_COLOR, PassiveStrategy())
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
        val mob = Mob(0, 0, Settings.CHARACTER_COLOR, PassiveStrategy())
        val p = Player()

        assertEquals(Settings.CHARACTER_HP, p.hp)
        mob.attackPlayer(p)
        assertEquals(Settings.CHARACTER_HP - Settings.MOB_DAMAGE, p.hp)
    }

    @Test
    fun testPlayerGotDamage() {
        val p = Player()
        assertEquals(Settings.CHARACTER_HP, p.hp)
        p.getDamage(Settings.MOB_DAMAGE)
        assertEquals(Settings.CHARACTER_HP - Settings.MOB_DAMAGE, p.hp)
    }

    @Test
    fun testMobMovement() {

    }
}