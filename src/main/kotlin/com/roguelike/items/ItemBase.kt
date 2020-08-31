package com.roguelike.items

import com.roguelike.enemies.player.Character
import java.awt.Graphics2D
import java.awt.image.BufferedImage

abstract class ItemBase(
        open val x: Int,
        open val y: Int
) {
    protected abstract val img: BufferedImage

    abstract fun draw(g: Graphics2D)
    abstract fun execute(p: Character)
    fun getPicture(): BufferedImage { return img }
}
