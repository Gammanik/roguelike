import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.geom.Ellipse2D
import javax.swing.JPanel
import graphics.*
import utils.Keys
import java.awt.Color

class GamePanel: JPanel(), KeyListener {
    private val map = Map()

    private var x1 = 0.0
    private var y1 = 0.0

    var velX = 20
    var velY = 20

    init { this.addKeyListener(this) }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g1 = g as Graphics2D
        g1.color = Color.BLACK;
        for (point in map.rectMap.values) {
            g1.color = point.col
            g1.fill3DRect(point.x * 10, point.y * 10, 10, 10, true);
        }

        g1.color = Color.BLACK;

        val circle =  Person(x1, y1, 20.0, 20.0)
        g1.fill(circle)
    }

    override fun keyTyped(p0: KeyEvent?) { }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0 == null)
            return

        when (p0.keyCode) {
            Keys.KEY_UP->  {
                if (y1 > 0) {
                    y1 -= velY
                }
            }
            Keys.KEY_DOWN -> {
                if (y < 600) {
                    y1 += velY
                }
            }
            Keys.KEY_RIGHT -> {
//                if (x < ) {
                    x1 += velX
//                }
            }
            Keys.KEY_LEFT -> x1 -= velX
        }
        repaint()
    }

    override fun keyReleased(p0: KeyEvent?) { }

    // todo: implement for handling multiple keys pressed
    private fun handleMovement() { }
}
