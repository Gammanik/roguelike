import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel
import graphics.*
import graphics.Map
import utils.Keys
import utils.Settings as set

class GamePanel(private val map : Map): JPanel(), KeyListener {

    private val person = Person(set.X_START_POINT, set.X_START_POINT)

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
        g.fill(person)
    }

    override fun keyTyped(p0: KeyEvent?) { }

    override fun keyPressed(p0: KeyEvent?) {

        if (p0 == null)
            return

        when (p0.keyCode) {

            Keys.KEY_UP->  {
                if (person.yCoordinate > 0 && !map.isWall(person.xCoordinate , person.yCoordinate - 1)
                        && !map.isWall(person.xCoordinate + 1, person.yCoordinate - 1)) {
                    person.yCoordinate -= set.VELOCITY
                }
            }

            Keys.KEY_DOWN -> {
                if (person.yCoordinate + 2 < set.Y_POINTS_COUNTS && !map.isWall(person.xCoordinate, person.yCoordinate + 2)
                        && !map.isWall(person.xCoordinate + 1, person.yCoordinate + 2)) {
                    person.yCoordinate += set.VELOCITY
                }
            }

            Keys.KEY_RIGHT -> {
                if (person.xCoordinate + 2 < set.X_POINTS_COUNTS && !map.isWall(person.xCoordinate + 2, person.yCoordinate)
                        && !map.isWall(  person.xCoordinate+ 2,  person.yCoordinate + 1)) {
                    person.xCoordinate += set.VELOCITY
                }
            }

            Keys.KEY_LEFT -> {
                if (person.xCoordinate > 0 && !map.isWall(person.xCoordinate - 1, person.yCoordinate)
                        && !map.isWall(person.xCoordinate - 1, person.yCoordinate + 1)) {
                    person.xCoordinate -= set.VELOCITY
                }
            }

        }
        person.updatePosition()
        repaint()

    }

    override fun keyReleased(p0: KeyEvent?) {}
}
