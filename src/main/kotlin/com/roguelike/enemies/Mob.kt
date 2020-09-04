package com.roguelike.enemies

import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.BehaviourStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.enemies.player.Character
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Move
import com.roguelike.utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.ActionListener
import java.awt.geom.Rectangle2D
import javax.swing.Timer

/** class for mob */
data class Mob(override var xCoordinate: Int, override var yCoordinate: Int, var currentBehavior: BehaviourStrategy)
    : Rectangle2D.Double(xCoordinate.toDouble(), yCoordinate.toDouble(), Settings.MOB_SIZE, Settings.MOB_SIZE),
    GameUnit {

    constructor(xCoordinate: Int, yCoordinate: Int, hp: Int, currentBehavior: BehaviourStrategy):
        this(xCoordinate, yCoordinate, currentBehavior) {
        this.hp = hp
        color = getMobColor()
    }

    private var color = getMobColor()

    var hp = Settings.MOB_HP; private set

    override fun draw(g: Graphics2D) {
        g.color = color
        g.fill(this)
    }

    private fun getMobColor(): Color {
        return when (currentBehavior) {
            is AggressiveStrategy -> Color.red
            is FunkyStrategy -> Color.cyan
            is PassiveStrategy -> Color.green
            else -> Color.ORANGE
        }
    }

    /** get damage from player */
    fun getDamage(dmg: Int) {
        color = Settings.MOB_GOT_DAMAGE_COLOR
        val t = Timer(Settings.ATTACK_DELAY) { color = Color.gray }
        t.isRepeats = false
        t.start()

        hp -= dmg
    }

    /** attack a main character */
    fun attackPlayer(player: Character) {
        player.getDamage(Settings.MOB_DAMAGE)
    }

    /** returns true if com.roguelike.ememies.player is dead for it to be deleted */
    fun behave(p: Character, checker: MapChecker): Boolean {
        currentBehavior.behave(p, this, checker)
        return hp <= 0
    }

    /** update mob position as a Rectangle2D */
    fun updatePosition() {
        x = xCoordinate.toDouble() * Settings.SQUARE_SIZE
        y = yCoordinate.toDouble() * Settings.SQUARE_SIZE
    }

    override fun getPointsCoordinates(): ArrayList<Pair<Int, Int>> {
        return arrayListOf(Pair(xCoordinate, yCoordinate))
    }
}
