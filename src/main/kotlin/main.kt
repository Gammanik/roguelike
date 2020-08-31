import com.roguelike.graphics.map_loading.LoadMapMenu
import com.roguelike.utils.Settings
import java.awt.Dimension
import java.awt.event.WindowEvent
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


/** starting the game */
fun main() {
    val loadMapMenu = LoadMapMenu()

    while ( !loadMapMenu.isMapLoaded) {
        Thread.sleep(Settings.LOAD_MAP_UPDATE_PERIOD)
    }

    val gameFrame = JFrame()
    gameFrame.isResizable = false
    gameFrame.defaultCloseOperation = EXIT_ON_CLOSE

    val gamePanel = GamePanel(loadMapMenu.getMap()) {
        println("game over")
        gameFrame.dispatchEvent(WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING))
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
