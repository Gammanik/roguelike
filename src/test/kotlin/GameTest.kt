import graphics.GameMap
import graphics.model.BadMapFileException
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Test
import java.io.File

class GameTest {

    @Test
    fun testTests() {
        assertEquals(2, 2)
    }

    @Test
    fun testCreateMap() {
        val m = GameMap()
        assertNotNull(m)
        assertTrue(m.rectMap.isNotEmpty())
    }

    @Test
    fun testCreateMapFromFile() {
        val filename = "src/test/resources/mapExample"
        val m = GameMap(File(filename))
        assertNotNull(m)
        assertTrue(m.rectMap.isNotEmpty())
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
