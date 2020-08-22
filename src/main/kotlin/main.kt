import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


fun main() {
    val s = GamePanel()
    val frame = JFrame()
    s.isFocusable = true

    s.size = Dimension(800, 600)
    frame.add(s)
    frame.isVisible = true
    frame.size = Dimension(1000, 800)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
}
