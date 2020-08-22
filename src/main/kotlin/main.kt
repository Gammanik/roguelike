import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


fun main() {
    val s = GamePanel()
    val frame = JFrame()
    s.isFocusable = true

    frame.add(s)
    frame.isVisible = true
    frame.size = Dimension(600, 400)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
}
