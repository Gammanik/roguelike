import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import utils.Settings


fun main() {
    val s = GamePanel()
    val frame = JFrame()
    s.isFocusable = true

    s.size = Dimension(Settings.WIDTH, Settings.HEIGHT)
    frame.add(s)
    frame.isVisible = true
    frame.size = Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
}
