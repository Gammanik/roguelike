package com.roguelike.items

import com.roguelike.enemies.player.Character
import com.roguelike.utils.Settings
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ArmorItem(override val x: Int, override val y: Int): ItemBase(x, y, false) {
    override val img: BufferedImage = ImageIO.read(File("src/main/resources/bulletproof_vest.png"))
    private val sz = Settings.SQUARE_SIZE
    var isWorn = false; private set

    override fun draw(g: Graphics2D) {
        g.drawImage(img, x * sz, y * sz, sz, sz, null)
    }

    override fun execute(p: Character) {
        isWorn = if ( !isWorn) {
            p.putOnArmor(this)
        } else {
            !p.takeOffArmor()
        }
    }
}