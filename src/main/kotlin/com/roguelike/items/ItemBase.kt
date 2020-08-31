package com.roguelike.items

import java.awt.Graphics2D

abstract class ItemBase(
        open val x: Int,
        open val y: Int
) {
    abstract fun draw(g: Graphics2D)
}
