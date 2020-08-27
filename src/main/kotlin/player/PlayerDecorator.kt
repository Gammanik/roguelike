package player

import player.Character
import utils.MapChecker
import java.awt.Color
import java.awt.Graphics2D

abstract class PlayerDecorator(val player: Character, checker: MapChecker) : Character() {

    override fun draw(g: Graphics2D) {
        g.color = Color.MAGENTA
        g.fill(this)
    }

    override var xCoordinate: Int
        get() = player.xCoordinate
        set(value) {}

    override var yCoordinate: Int
        get() = player.yCoordinate
        set(value) {}

    init {
        updatePosition()
    }
}
