import ememies.AggressiveBehaviourStrategy
import ememies.Mob
import ememies.PassiveBehaviourStrategy
import graphics.GameMap
import graphics.model.*
import utils.Keys
import java.awt.Color
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

    private val checker = MapChecker(gameMap)
    private var person : Character = Player(checker)
    private val timer = Timer(44, this)

    private val mobs = listOf(
            Mob(10, 10, 100, Color.red, AggressiveBehaviourStrategy(), checker),
            Mob(12, 14, 100, Color.CYAN, PassiveBehaviourStrategy(), checker))

    private var isKeyUp = false; private var isKeyDown = false
    private var isKeyLeft = false; private var isKeyRight = false

    init {
        this.addKeyListener(this)
        timer.start()
    }

    fun endConfusion() {
        person = (person as ConfusionSpellDecorator).player
    }

    override fun getPreferredSize() : Dimension {
        return Dimension(800, 600)
    }

    override fun actionPerformed(p0: ActionEvent?) {
        if (isKeyUp) person.stepUp()
        if (isKeyDown) person.stepDown()
        if (isKeyRight) person.stepRight()
        if (isKeyLeft) person.stepLeft()

        person.updatePosition()
        for (m in mobs) {
            m.behave(person)
            m.updatePosition()
        }

        val confusePoint = checker.checkForConfuse(person)

        if (confusePoint.isNotEmpty()) {
            person = ConfusionSpellDecorator(person, checker)
            for (point in confusePoint) {
                point.col = utils.Settings.BACKGROUND_COLOR
                val timer = Timer(2500, ConfusionListener(this))
                timer.isRepeats = false
                timer.start()
            }
        }

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

        for (m in mobs) {
            g1.color = m.color
            g1.fill(m)
        }
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
