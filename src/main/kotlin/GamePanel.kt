import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.roguelike.enemies.Mob
import com.roguelike.enemies.MobListener
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import com.roguelike.saving.*
import com.roguelike.utils.Keys
import com.roguelike.utils.MapChecker
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import javax.swing.JPanel
import javax.swing.Timer
import com.roguelike.utils.Settings as set


/** The main game window */
class GamePanel(var gameMap: GameMap, playerDeadCallback: () -> Unit) : JPanel(), KeyListener, ActionListener {

    var mobs = mutableListOf<Mob>()
    var player : Character = Player(); private set
    var items = mutableListOf<ItemBase>()

    val checker = MapChecker(gameMap, mobs, player)

    private var timer = Timer(set.DELAY, this)
    private var mobAttackTimer: Timer = Timer(100, MobListener(checker, player))

    private var isKeyUp = false; private var isKeyDown = false
    private var isKeyLeft = false; private var isKeyRight = false
    private var isAttackPressed = false

//    fun getItems(): List<ItemBase> {
//        return items
//    }

    init {
        player.addDeadCallback(playerDeadCallback)
        this.addKeyListener(this)
        addMobs()
        addItems()
        timer.start()
        mobAttackTimer.start()
    }

    constructor(gameMap: GameMap, mobs: MutableList<Mob>, character: Character,
                items: MutableList<ItemBase>, playerDeadCallback: () -> Unit): this(gameMap, playerDeadCallback) {
        this.gameMap = gameMap
        this.player = character
        this.mobs = mobs
        this.items = items
        timer = Timer(set.DELAY, this)
        mobAttackTimer = Timer(100, MobListener(checker, player))
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
        checkItems()
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val gameField = g as Graphics2D

        for (point in gameMap.getRectMap().values) {
            gameField.color = point.col
            gameField.fill3DRect(
                point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                set.SQUARE_SIZE, set.SQUARE_SIZE, true
            )
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

//            saveGame()

            isAttackPressed = true
            val t = Timer(set.ATTACK_DELAY) { isAttackPressed = false }
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

            if (m.behave(player, checker)) {
                player.addExp(30)
                it.remove()
            }
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

    private fun checkItems() {
        checker.getClosestItems(items).forEach {
            it.execute(player)
            items.remove(it)
        }
    }

    /** add an item to a map */
    private fun addItem(item: ItemBase) {
        items.add(item)
    }

    private fun addItems() {
        addItem(AidItem(2, 20))
        addItem(AidItem(5, 35))
        addItem(AidItem(4, 15))
        addItem(PowerUpItem(5, 5))
        addItem(PowerUpItem(10, 10))
        addItem(PowerUpItem(10, 15))
        addItem(PoisonItem(40, 15))
    }

    /** add mob to a map */
    fun addMob(newMob: Mob) {
        mobs.add(newMob)
    }

    private fun addMobs() {
        val aggressiveStrategy = AggressiveStrategy()
        val funkyStrategy = FunkyStrategy()
        val passiveStrategy = PassiveStrategy()

        var gson: Gson = GsonBuilder()
            .registerTypeAdapter(Character::class.java, PlayerSerializer())
            .registerTypeAdapter(Player::class.java, PlayerSerializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerSerializer())
            .registerTypeAdapter(Mob::class.java, MobSerializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelSerializer())
            .create()
        val json: String = gson.toJson(Mob(10, 10, aggressiveStrategy))

        gson = GsonBuilder()
            .registerTypeAdapter(Mob::class.java, MobDeserializer())
            .create()


        val mob: Mob = gson.fromJson(json, Mob::class.java)

        addMob(mob)


        addMob(Mob(10, 10, aggressiveStrategy))
        addMob(Mob(30, 8, funkyStrategy))
        addMob(Mob(25, 17, aggressiveStrategy))
        addMob(Mob(17, 20, aggressiveStrategy))
        addMob(Mob(2, 2, passiveStrategy))
        addMob(Mob(27, 27, aggressiveStrategy))
        addMob(Mob(30, 35, aggressiveStrategy))
        addMob(Mob(32, 40, aggressiveStrategy))
        addMob(Mob(32, 45, aggressiveStrategy))
    }

    private fun saveGame() {
        val file = File("src/main/resources/snapshots", "snapshot")
        file.createNewFile()
        val writer = BufferedWriter(FileWriter(file));
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Character::class.java, PlayerSerializer())
            .registerTypeAdapter(Player::class.java, PlayerSerializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerSerializer())
            .registerTypeAdapter(Mob::class.java, MobSerializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelSerializer())
            .registerTypeAdapter(GameMap::class.java, MapSerializer())
            .registerTypeAdapter(ItemBase::class.java, ItemSerializer())
            .registerTypeAdapter(AidItem::class.java, ItemSerializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemSerializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemSerializer())
            .create()
        val json = gson.toJson(this)
        println(json)
        writer.write(json)
        writer.close()
    }
}
