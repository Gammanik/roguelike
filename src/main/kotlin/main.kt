import com.google.gson.GsonBuilder
import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.graphics.map_loading.LoadMapMenu
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import com.roguelike.saving.*
import com.roguelike.utils.Settings
import java.awt.Dimension
import java.awt.event.WindowEvent
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


/** starting the game */
fun main() {
    val loadMapMenu = LoadMapMenu()

    while ( !loadMapMenu.isMapLoaded) {
        Thread.sleep(Settings.LOAD_MAP_UPDATE_PERIOD)
    }

    val loadGame = false

//    gson = GsonBuilder()
//        .registerTypeAdapter(DwarvesBand::class.java, DwarvesBandDeserializer())
//        .registerTypeAdapter(FacialHair::class.java, FacialHairDeserializer())
//        .registerTypeAdapter(Dwarf::class.java, DwafDeserializer())
//        .create()

    val gameFrame = JFrame()
    gameFrame.isResizable = false
    gameFrame.defaultCloseOperation = EXIT_ON_CLOSE

    val gamePanel: GamePanel

    if (!loadGame) {
        gamePanel = GamePanel(loadMapMenu.getMap()) {
            println("game over")
            gameFrame.dispatchEvent(WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING))
        }
    } else {
        val file = File("src/main/resources/snapshots", "snapshot")
        val content = String(Files.readAllBytes(file.toPath()))
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Character::class.java, PlayerDeserializer())
            .registerTypeAdapter(Player::class.java, PlayerDeserializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerDeserializer())
            .registerTypeAdapter(Mob::class.java, MobDeserializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelDeserializer{
                println("game over")
                gameFrame.dispatchEvent(WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING))
            })
            .registerTypeAdapter(GameMap::class.java, MapDeserializer())
            .registerTypeAdapter(ItemBase::class.java, ItemDeserializer())
            .registerTypeAdapter(AidItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemDeserializer())
            .create()
        gamePanel = gson.fromJson(content, GamePanel::class.java)
    }
    val infoMenuPanel = InfoMenuPanel(gamePanel)

    val contPane = gameFrame.contentPane
    contPane.layout = BoxLayout(contPane, BoxLayout.Y_AXIS)
    contPane.add(gamePanel)
    contPane.add(infoMenuPanel)

    gamePanel.size = Dimension(Settings.WIDTH, Settings.HEIGHT)

    gameFrame.add(gamePanel)
    gameFrame.add(infoMenuPanel)
    gameFrame.pack()

    gameFrame.isVisible = true
    gamePanel.isFocusable = true
    infoMenuPanel.isFocusable = true
}
