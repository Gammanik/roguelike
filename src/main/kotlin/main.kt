import graphics.LoadMapMenu
import utils.Settings
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


/** starting the game */
fun main() {
    val loadMapMenu = LoadMapMenu()

    while (!loadMapMenu.isMapLoaded) {
        Thread.sleep(Settings.LOAD_MAP_UPDATE_PERIOD)
    }

    val gameFrame = JFrame()
    gameFrame.isResizable = false
    gameFrame.defaultCloseOperation = EXIT_ON_CLOSE

    val gamePanel = GamePanel(loadMapMenu.getMap())
    gamePanel.size = Dimension(Settings.WIDTH, Settings.HEIGHT)
    gameFrame.add(gamePanel)

    gameFrame.pack()
    gameFrame.isVisible = true
    gamePanel.isFocusable = true
}
