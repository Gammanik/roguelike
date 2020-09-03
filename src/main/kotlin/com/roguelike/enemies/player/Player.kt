package com.roguelike.enemies.player

import com.roguelike.graphics.model.Explosion
import com.roguelike.items.ItemBase
import com.roguelike.utils.MapChecker
import com.roguelike.utils.Settings
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.Timer

/** Player class.
 * x, y represents the current com.roguelike.ememies.player coordinates in pixels
 * xCoordinate, yCoordinate represents the com.roguelike.ememies.player coordinates in squares
 * */
class Player() : Character() {

    private var color = Color.BLACK
    private var ex: Explosion? = null

    override var xCoordinate: Int = Settings.X_START_POINT
    override var yCoordinate: Int = Settings.Y_START_POINT

    private val img: BufferedImage = ImageIO.read(File("src/main/resources/pngegg.png"))
    private val sz = Settings.SQUARE_SIZE
    private val szMob = 20

    override fun draw(g: Graphics2D) {
        g.drawImage(img, xCoordinate * sz, yCoordinate * sz, szMob, szMob, null)
    }

    constructor(x: Int, y: Int, hp: Int, lvl: Int, exp: Int, expMax: Int, items: MutableList<ItemBase>) : this() {
        xCoordinate = x
        yCoordinate = y
        this.hp = hp
        this.lvl = lvl
        this.exp = exp
        this.expMax = expMax
        setCurrentItems(items)
    }

    override fun drawAttacking(g: Graphics2D) {
        ex?.r = ex?.r?.plus(2)!!
        ex?.draw(g, ex?.r!!, currAttackPower)
    }

    override fun getDamage(dmg: Int) {
        color = Color.ORANGE
        val t = Timer(Settings.ATTACK_DELAY) { color = Color.BLACK }
        t.isRepeats = false
        t.start()

        val damageCoefficient = if (armor == null) 2 else 1

        hp -= dmg * damageCoefficient

        if (hp <= 0) {
            playerDeadCallback?.let { it() }
        }
    }

    override fun attackClosestMobs(checker: MapChecker) {
        ex = Explosion(xCoordinate, yCoordinate)
        val mobs = checker.getClosestMobs()

        for (m in mobs) {
            m.getDamage(currAttackPower)
        }
    }
}
