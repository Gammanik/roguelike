package com.roguelike.items

import com.roguelike.utils.Settings
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class PowerUpItem(override val x: Int, override val y: Int): ItemBase(x, y) {

    private val img: BufferedImage = ImageIO.read(File("src/main/resources/bomb.png"))
    private val sz = Settings.SQUARE_SIZE

    override fun draw(g: Graphics2D) {
        g.drawImage(img, x * sz, y  * sz, 1 * sz, 1 * sz, null)
    }
}
