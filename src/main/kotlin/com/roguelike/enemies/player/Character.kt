package com.roguelike.enemies.player

import GamePanel
import com.roguelike.enemies.GameUnit
import com.roguelike.items.ItemBase
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Settings
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import kotlin.math.pow
import kotlin.math.sqrt

/** abstract class for main character */
abstract class Character() : Ellipse2D.Double(0.0, 0.0,
        Settings.CHARACTER_DIAMETER, Settings.CHARACTER_DIAMETER),
    GameUnit {

    var hp = Settings.CHARACTER_MAX_HP; protected set
    var exp = 0; protected set
    var lvl = 1; protected set
    var expMax = 100; protected set
    internal var currAttackPower = 20

    private var currentItems = mutableListOf<ItemBase>()

    var playerDeadCallback: (() -> Unit)? = null
    fun addDeadCallback(cb: (() -> Unit)) {
        playerDeadCallback = cb
    }

    fun setCurrentItems(list: MutableList<ItemBase>) {
        currentItems = list
    }

    fun getCurrentItems(): List<ItemBase> {
        return currentItems
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate), Pair(xCoordinate + 1, yCoordinate),
                    Pair(xCoordinate, yCoordinate + 1), Pair(xCoordinate + 1, yCoordinate + 1))
    }

    /** get distance to the (x, y) point) */
    fun getDistance(x: Int, y: Int): kotlin.Double {
        return sqrt( (x - xCoordinate).toDouble().pow(2)  + (y - yCoordinate).toDouble().pow(2))
    }

    /** update mob position as a Ellipse2D */
    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    /** draw player in attack */
    open fun drawAttacking(g: Graphics2D) {}

    /** attack closest to the player mobs */
    open fun attackClosestMobs(checker: MapChecker) {}

    /** get damage from mobs */
    fun addExp(value: Int) {
        exp += value
        if (exp >= expMax) {
            exp %= expMax
            lvl++
            expMax *= 2
        }
    }

    /** get damage from mobs */
    open fun getDamage(dmg: Int) {}

    fun putItemOn(item: ItemBase) {
        currentItems.add(item)
    }

    fun putItemOff(panel: GamePanel) {
//        panel.addItem()
    }

    fun addHp(v: Int) {
        hp = if ((hp + v) > Settings.CHARACTER_MAX_HP) 100 else hp + v
    }

    fun addAttackPower(v: Int) {
        currAttackPower += v
    }
}
