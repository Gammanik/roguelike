package com.roguelike

import com.roguelike.commands.CommandController
import com.roguelike.commands.SaveGameCommand
import com.roguelike.enemies.Mob
import com.roguelike.enemies.MobListener
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.graphics.model.SavePopUp
import com.roguelike.items.*
import com.roguelike.utils.Keys
import com.roguelike.utils.MapChecker
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.AbstractAction
import javax.swing.JPanel
import javax.swing.KeyStroke
import javax.swing.Timer
import com.roguelike.utils.Settings as set

/** The main game window */
class GamePanel() : JPanel(), KeyListener, ActionListener {

    lateinit var gameMap: GameMap; private set
    lateinit var mobs: MutableList<Mob>; private set
    lateinit var player : Character; private set
    lateinit var items: MutableList<ItemBase>; private set
    private lateinit var checker: MapChecker

    private lateinit var timer: Timer
    private lateinit var mobAttackTimer: Timer
    private lateinit var mobsRegenerationTimer: Timer

    private var isKeyUp = false; private var isKeyDown = false
    private var isKeyLeft = false; private var isKeyRight = false
    private var isAttackPressed = false

    private var savePopUp: SavePopUp? = null

    private fun initGame(playerDeadCallback: () -> Unit) {
        this.checker = MapChecker(gameMap, mobs, player)
        timer = Timer(set.DELAY, this)
        mobAttackTimer = Timer(100, MobListener(checker, player))
        player.addDeadCallback(playerDeadCallback)
        this.addKeyListener(this)
        timer.start()
        mobAttackTimer.start()

        mobsRegenerationTimer = Timer(400) {
            val cdrX = (0..set.X_POINTS_COUNTS).random()
            val cdrY = (0..set.Y_POINTS_COUNTS).random()

            val notInWall = checker.mapCheckHelper(listOf(Pair(cdrX, cdrY)))
            if (notInWall) {
                mobs.add(Mob(cdrX, cdrY, 100, AggressiveStrategy()))
            }
        }
        mobsRegenerationTimer.start()

        CommandController.getInstance()
            .registerCommand("T", SaveGameCommand(this@GamePanel),
                this.getInputMap(WHEN_IN_FOCUSED_WINDOW), this.actionMap)
    }

    constructor(gameMap: GameMap, playerDeadCallback: () -> Unit): this() {
        this.gameMap = gameMap
        mobs = mutableListOf()
        player = Player()
        items = mutableListOf()

        initGame(playerDeadCallback)
        addMobs()
        addItems()
    }

    constructor(gameMap: GameMap, mobs: MutableList<Mob>, character: Character,
                items: MutableList<ItemBase>, playerDeadCallback: () -> Unit): this() {
        this.gameMap = gameMap
        this.mobs = mobs
        this.player = character
        this.items = items

        initGame(playerDeadCallback)
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
            gameField.fill3DRect(point.x * set.SQUARE_SIZE, point.y * set.SQUARE_SIZE,
                set.SQUARE_SIZE, set.SQUARE_SIZE, true)
        }

        for (m in mobs) m.draw(g)
        for (item in items) item.draw(g)
        player.draw(gameField)

        if (isAttackPressed) {
            player.drawAttacking(gameField)
        }

        savePopUp?.draw(g)
    }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0?.keyCode == Keys.KEY_ATTACK) {
            player.attackClosestMobs(checker)

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

    fun checkItems() {
        checker.getClosestItems(items).forEach {
            if (player.putItemOn(it)) {
                items.remove(it)
            }
        }
    }

    /** add an item to a map */
    fun addItem(item: ItemBase) {
        items.add(item)
    }

    private fun addItems() {
        addItem(AidItem(2, 20))
        addItem(AidItem(5, 35))
        addItem(PowerUpItem(5, 3))
        addItem(PowerUpItem(6, 3))
        addItem(ArmorItem(8, 8))

        addItem(AidItem(0, 25))
        addItem(ArmorItem(2, 15))
        addItem(AidItem(4, 15))
        addItem(PoisonItem(6, 15))
        addItem(AidItem(8, 15))
        addItem(PowerUpItem(10, 15))
        addItem(PowerUpItem(11, 18))

        addItem(PowerUpItem(10, 10))
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

    fun showSavePopUp() {
        savePopUp = SavePopUp()
        val timer = Timer(2500) { savePopUp = null }
        timer.isRepeats = false
        timer.start()
    }

}
