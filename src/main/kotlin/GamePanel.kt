import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel
import graphics.*
import utils.Keys
import utils.Settings as set

class GamePanel: JPanel(), KeyListener {
    private val map = Map()

    private var x1 = set.X_START_POINT
    private var y1 = set.Y_START_POINT

    init { this.addKeyListener(this) }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g1 = g as Graphics2D

        for (point in map.rectMap.values) {
            g1.color = point.col
            g1.fill3DRect(point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                set.SQUARE_SIZE, set.SQUARE_SIZE, true);
        }

        g1.color = set.CHARACTER_COLOR

        val circle =  Person(x1 * set.SQUARE_SIZE, y1 * set.SQUARE_SIZE,
            set.CHARACTER_DIAMETER.toDouble(), set.CHARACTER_DIAMETER.toDouble())
        g1.fill(circle)
    }

    override fun keyTyped(p0: KeyEvent?) { }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0 == null)
            return

        when (p0.keyCode) {

            Keys.KEY_UP->  {
                if (y1 > 0 && !map.isWall(x1 , y1 - 1) && !map.isWall(x1 + 1, y1 - 1)) {
                    y1 -= set.VELOCITY
                }
            }

            Keys.KEY_DOWN -> {
                if (y1 + 2 < set.Y_POINTS_COUNTS && !map.isWall(x1, y1 + 2) && !map.isWall(x1 + 1, y1 + 2)) {
                    y1 += set.VELOCITY
                }
            }

            Keys.KEY_RIGHT -> {
                if (x1 + 2 < set.X_POINTS_COUNTS && !map.isWall(x1 + 2, y1) && !map.isWall(x1 + 2, y1 + 1)) {
                    x1 += set.VELOCITY
                }
            }

            Keys.KEY_LEFT -> {
                if (x1 > 0 && !map.isWall(x1 - 1, y1) && !map.isWall(x1 - 1, y1 + 1)) {
                    x1 -= set.VELOCITY
                }
            }

        }
        repaint()
    }

    override fun keyReleased(p0: KeyEvent?) { }

    // todo: implement for handling multiple keys pressed
    private fun handleMovement() { }
}
