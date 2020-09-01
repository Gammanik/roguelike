import com.roguelike.graphics.map_loading.LoadMapMenu
import com.roguelike.utils.Settings
import java.awt.Dimension
import java.awt.event.WindowEvent
import java.io.File
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


/** starting the game */
fun main() {
    val gameFrame = JFrame()
    gameFrame.isResizable = false
    gameFrame.defaultCloseOperation = EXIT_ON_CLOSE

    val loadMapMenu = LoadMapMenu {
        println("game over")
        File("src/main/resources/snapshots", "snapshot").delete()
        gameFrame.dispatchEvent(WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING))
    }

    while ( !loadMapMenu.isMapLoaded) {
        Thread.sleep(Settings.LOAD_MAP_UPDATE_PERIOD)
    }

    val gamePanel = loadMapMenu.getGamePanel()
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
