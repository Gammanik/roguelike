import graphics.GameMap
import org.junit.Test
import utils.MapChecker
import java.io.File

class CheckerTest {

    @Test
    fun testReturnsClosestMobs() {
        val mapFilename = "src/test/resources/mapExample"
        val gameMap = GameMap(File(mapFilename))

//        val checker = MapChecker(gameMap, )
    }
}