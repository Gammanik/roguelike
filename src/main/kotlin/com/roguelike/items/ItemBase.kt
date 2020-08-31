package com.roguelike.items

import java.awt.Graphics2D

abstract class ItemBase(x: Int, y: Int) {
    abstract fun draw(g: Graphics2D)
}