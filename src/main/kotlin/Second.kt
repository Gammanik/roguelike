import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.geom.Ellipse2D
import javax.swing.JPanel

class Second: JPanel(), ActionListener, KeyListener {
    private var x1 = 0.0
    private var y1 = 0.0

    var velX = 2
    var velY = 2

    init { this.addKeyListener(this) }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2 = g as Graphics2D
        val circle: Ellipse2D = Ellipse2D.Double(x1, y1, 20.0, 20.0)
        g2.fill(circle)

        repaint()
    }

    override fun actionPerformed(p0: ActionEvent?) {
        if (x1 < 0 || x1 > 560)
            velX = -velX

        if (y1 < 0 || y1 > 360)
            velY = -velY

        x1 += velX
        y1 += velY

        repaint()
    }

    override fun keyTyped(p0: KeyEvent?) { }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0 == null)
            return

        when (p0.keyCode) {
            Keys.KEY_UP -> y1 -= velY
            Keys.KEY_DOWN -> y1 += velY
            Keys.KEY_RIGHT -> x1 += velX
            Keys.KEY_LEFT -> x1 -= velX
        }

        repaint()
    }

    override fun keyReleased(p0: KeyEvent?) { }

    // todo: implement for handling multiple keys pressed
    private fun handleMovement() { }
}
