import com.roguelike.enemies.*
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.graphics.GameMap
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.items.ItemBase
import com.roguelike.items.PowerUpItem
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
class GamePanel(private val gameMap: GameMap, private val playerDeadCallback: () -> Unit) : JPanel(), KeyListener, ActionListener {

    private val mobs = mutableListOf<Mob>()
    var player : Character = Player(); private set
    private val items = mutableListOf<ItemBase>()

    private val checker = MapChecker(gameMap, mobs, player)

    private val timer = Timer(set.DELAY, this)
    private val mobAttackTimer: Timer = Timer(100, MobListener(checker, player))

    private var isKeyUp = false; private var isKeyDown = false
    private var isKeyLeft = false; private var isKeyRight = false
    private var isAttackPressed = false

    init {
        player.addDeadCallback(playerDeadCallback)
        this.addKeyListener(this)
        addMobs()
        addItems()
        timer.start()
        mobAttackTimer.start()
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

        behaveMobs()
        checkConfuse()
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val gameField = g as Graphics2D

        for (point in gameMap.getRectMap().values) {
            gameField.color = point.col
            gameField.fill3DRect(point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                set.SQUARE_SIZE, set.SQUARE_SIZE, true)
        }

        player.draw(gameField)
        for (m in mobs) m.draw(g)
        for (item in items) item.draw(g)

        if (isAttackPressed) {
            player.drawAttacking(gameField)
        }
    }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0?.keyCode == Keys.KEY_ATTACK) {
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

    private fun behaveMobs() {
        val it = mobs.iterator()
        while (it.hasNext()) {
            val m = it.next()
            m.updatePosition()

            if (m.behave(player, checker))
                it.remove()
        }
    }

    private fun checkConfuse() {
        val confusePoint = checker.checkForConfuse(player)
        if (confusePoint.isNotEmpty()) {
            player = ConfusionSpellDecorator(player)
            for (point in confusePoint) {
                point.col = com.roguelike.utils.Settings.BACKGROUND_COLOR
                val timer = Timer(2500) { this.endConfusion() }
                timer.isRepeats = false
                timer.start()
            }
        }
    }

    /** add an item to a map */
    private fun addItem(item: ItemBase) {
        items.add(item)
    }

    private fun addItems() {
        addItem(PowerUpItem(20, 20))
    }

    /** add mob to a map */
    fun addMob(newMob: Mob) {
        mobs.add(newMob)
    }

    private fun addMobs() {
        val aggressiveStrategy = AggressiveStrategy()
        val funkyStrategy = FunkyStrategy()
        val passiveStrategy = PassiveStrategy()

        addMob(Mob(10, 10, aggressiveStrategy))
        addMob(Mob(30, 8, funkyStrategy))
        addMob(Mob(25, 17,aggressiveStrategy))
        addMob(Mob(17, 20, aggressiveStrategy))
        addMob(Mob(2, 2, passiveStrategy))
        addMob(Mob(27, 27, aggressiveStrategy))
        addMob(Mob(30, 35, aggressiveStrategy))
        addMob(Mob(32, 40, aggressiveStrategy))
        addMob(Mob(32, 45, aggressiveStrategy))
    }
}
