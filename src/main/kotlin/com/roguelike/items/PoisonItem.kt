package com.roguelike.items

import com.roguelike.enemies.player.Character
import com.roguelike.utils.Settings
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/** give damage to player **/
class PoisonItem(override val x: Int, override val y: Int): ItemBase(x, y, true) {
    override val img: BufferedImage = ImageIO.read(File("src/main/resources/confusion.png"))
    private val sz = Settings.SQUARE_SIZE

    override fun draw(g: Graphics2D) {
        g.drawImage(img, x * sz, y * sz, sz, sz, null)
    }

    override fun execute(p: Character) {
        p.getDamage(20)
    }
}
