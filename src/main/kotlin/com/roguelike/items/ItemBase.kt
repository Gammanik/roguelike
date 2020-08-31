package com.roguelike.items

import com.roguelike.enemies.player.Character
import java.awt.Graphics2D

abstract class ItemBase(
        open val x: Int,
        open val y: Int
) {
    abstract fun draw(g: Graphics2D)
    // todo: make abstract
    open fun execute(p: Character) {}
}
