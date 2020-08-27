import com.roguelike.ememies.*
import com.roguelike.ememies.behaviour.AggressiveStrategy
import com.roguelike.ememies.behaviour.FunkyStrategy
import com.roguelike.ememies.behaviour.PassiveStrategy
import com.roguelike.graphics.GameMap
import com.roguelike.ememies.player.Character
import com.roguelike.ememies.player.ConfusionSpellDecorator
import com.roguelike.ememies.player.Player
import com.roguelike.utils.Keys
import com.roguelike.utils.MapChecker
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
import com.roguelike.utils.Settings as set

/** The main game window */
class GamePanel(private val gameMap: GameMap) : JPanel(), KeyListener, ActionListener {

    private val mobs = mutableListOf<Mob>()
    private var player : Character = Player()

    private val checker = MapChecker(gameMap, mobs, player)

    private val timer = Timer(set.DELAY, this)
    private val mobAttackTimer: Timer = Timer(100, MobListener(checker, player))

    private var isKeyUp = false; private var isKeyDown = false
    private var isKeyLeft = false; private var isKeyRight = false

    private var isAttackPressed = false

    init {
        addMob(Mob(10, 10, Color.RED, AggressiveStrategy()))
        addMob(Mob(30, 8, Color.CYAN, FunkyStrategy()))
        addMob(Mob(20, 10, Color.RED, AggressiveStrategy()))
        addMob(Mob(25, 17, Color.RED, AggressiveStrategy()))
        addMob(Mob(13, 10, Color.RED, AggressiveStrategy()))
        addMob(Mob(2, 2, Color.GREEN, PassiveStrategy()))
        this.addKeyListener(this)
        timer.start()
        mobAttackTimer.start()
    }

    fun addMob(newMob: Mob) {
        mobs.add(newMob)
    }

    private fun endConfusion() {
        player = (player as ConfusionSpellDecorator).player
    }

    override fun getPreferredSize() : Dimension {
        return Dimension(800, 600)
    }

    override fun actionPerformed(p0: ActionEvent?) {
        if (isKeyUp) player.stepUp(checker)
        if (isKeyDown) player.stepDown(checker)
        if (isKeyRight) player.stepRight(checker)
        if (isKeyLeft) player.stepLeft(checker)

        player.updatePosition()

        val it = mobs.iterator()
        while (it.hasNext()) {
            val m = it.next()
            m.updatePosition()

            if (m.behave(player, checker))
                it.remove()
        }

        val confusePoint = checker.checkForConfuse(player)
        if (confusePoint.isNotEmpty()) {
            player = ConfusionSpellDecorator(player)
            for (point in confusePoint) {
                point.col = com.roguelike.utils.Settings.BACKGROUND_COLOR
                val timer = Timer(2500, ActionListener { this.endConfusion() })
                timer.isRepeats = false
                timer.start()
            }
        }

        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g1 = g as Graphics2D

        for (point in gameMap.getRectMap().values) {
            g1.color = point.col
            g1.fill3DRect(point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                set.SQUARE_SIZE, set.SQUARE_SIZE, true)
        }

        player.draw(g1)
        for (m in mobs)
            m.draw(g)

        if (isAttackPressed) {
            player.drawAttacking(g1)
        }
    }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0 == null) return

        if (p0.keyCode == Keys.KEY_ATTACK) {
            player.attackClosestMobs(checker)

            isAttackPressed = true
            val t = Timer(set.ATTACK_DELAY, ActionListener { isAttackPressed = false })
            t.isRepeats = false
            t.start()
        }
    }

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
