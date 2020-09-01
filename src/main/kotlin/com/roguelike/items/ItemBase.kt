package com.roguelike.items

import com.roguelike.enemies.player.Character
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/** represents custom item on map
 * [com.roguelike.enemies.player.Player] could put the item on or put off
 * item could be executed (affects the player somehow) or dropped **/
abstract class ItemBase(
        open val x: Int,
        open val y: Int
) {
    protected abstract val img: BufferedImage

    abstract fun draw(g: Graphics2D)
    abstract fun execute(p: Character)
    fun getPicture(): BufferedImage { return img }
}
