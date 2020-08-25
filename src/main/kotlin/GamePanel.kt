import graphics.GameMap
import graphics.model.Player
import utils.Keys
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel
import javax.swing.Timer
import utils.Settings as set

/** The main game window */
class GamePanel(private val gameMap: GameMap) : JPanel(), KeyListener, ActionListener {

    private val person = Player(set.X_START_POINT, set.Y_START_POINT)
    private val timer = Timer(44, this)

    private var isKeyUp = false
    private var isKeyDown = false
    private var isKeyLeft = false
    private var isKeyRight = false

    init {
        this.addKeyListener(this)
        timer.start()
    }

    override fun getPreferredSize() : Dimension {
        return Dimension(800, 600)
    }

    override fun actionPerformed(p0: ActionEvent?) {
        if (isKeyUp) {
            if (person.yCoordinate > 0 && !gameMap.isWall(person.xCoordinate, person.yCoordinate - 1)
                    && !gameMap.isWall(person.xCoordinate + 1, person.yCoordinate - 1)) {
                person.yCoordinate -= set.VELOCITY
            }
        }

        if (isKeyDown) {
            if (person.yCoordinate + 2 < set.Y_POINTS_COUNTS && !gameMap.isWall(person.xCoordinate, person.yCoordinate + 2)
                    && !gameMap.isWall(person.xCoordinate + 1, person.yCoordinate + 2)) {

                person.yCoordinate += set.VELOCITY
            }
        }

        if (isKeyRight) {
            if (person.xCoordinate + 2 < set.X_POINTS_COUNTS && !gameMap.isWall(person.xCoordinate + 2, person.yCoordinate)
                    && !gameMap.isWall(person.xCoordinate + 2, person.yCoordinate + 1)) {
                person.xCoordinate += set.VELOCITY
            }
        }

        if (isKeyLeft) {
            if (person.xCoordinate > 0 && !gameMap.isWall(person.xCoordinate - 1, person.yCoordinate)
                    && !gameMap.isWall(person.xCoordinate - 1, person.yCoordinate + 1)) {
                person.xCoordinate -= set.VELOCITY
            }
        }

        person.updatePosition()
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g1 = g as Graphics2D

        for (point in gameMap.rectMap.values) {
            g1.color = point.col
            g1.fill3DRect(point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                    set.SQUARE_SIZE, set.SQUARE_SIZE, true)
        }

        g1.color = set.CHARACTER_COLOR
        g.fill(person)
    }

    override fun keyPressed(p0: KeyEvent?) { }

    override fun keyReleased(p0: KeyEvent?) {
        if (p0 == null) return
        updateDirection(p0.keyChar, false)
    }

    override fun keyTyped(p0: KeyEvent?) {
        if (p0 == null) return
        updateDirection(p0.keyChar, true)
    }

    private fun updateDirection(keyChar: Char, newBool: Boolean) {
        when (keyChar) {
            Keys.KEY_UP -> isKeyUp = newBool
            Keys.KEY_DOWN -> isKeyDown = newBool
            Keys.KEY_RIGHT -> isKeyRight = newBool
            Keys.KEY_LEFT -> isKeyLeft = newBool
        }
    }
}
