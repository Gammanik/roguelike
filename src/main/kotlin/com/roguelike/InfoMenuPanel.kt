package com.roguelike
import com.roguelike.commands.*
import com.roguelike.utils.Keys
import com.roguelike.utils.Settings
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

/** panel for showing player hp, exp, level and items
 * adds keyboard bindings to interact with items (switch, drop or execute)
 * if you want to choose specific item you can use number buttons (12345678)**/
class InfoMenuPanel(private val gamePanel: GamePanel) : JPanel(), ActionListener {

    private val timer: Timer = Timer(100, this)
    private val controller = CommandController.getInstance()

    init {
        controller.registerCommand(Keys.KEY_EXECUTE, ExecuteItemCommand(gamePanel.player),
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW), this.actionMap)

        controller.registerCommand(Keys.DROP, DropItemCommand(gamePanel.player),
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW), this.actionMap)

        controller.registerCommand(Keys.SWITCH_ITEM, SwitchItemCommand(gamePanel.player),
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW), this.actionMap)

        for (i in 1..Settings.MAX_ITEMS) {
            controller.registerCommand(i.toString(), ChooseItemNumCommand(gamePanel.player, i - 1),
                this.getInputMap(WHEN_IN_FOCUSED_WINDOW), this.actionMap)
        }

        timer.start()
    }

    private fun bindKey(key: String, fn: () -> Unit) {
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key)
        this.actionMap.put(key, object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) { fn() }
        })
    }

    override fun actionPerformed(e: ActionEvent?) {
        this.repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val infoPanel = g as Graphics2D
        fillPanel(g)
        paintHpOrLvl(g, gamePanel.player.hp, 100, Color.RED, 5)
        paintHpOrLvl(g, gamePanel.player.exp, gamePanel.player.expMax, Color.YELLOW, 30)
        painLvlOval(g)
        drawCurrentItems(infoPanel)
    }

    override fun getPreferredSize() : Dimension {
        return Dimension(800, 100)
    }

    private fun drawCurrentItems(g: Graphics2D) {
        val items = gamePanel.player.getCurrentItems()

        var i = 0
        val armor = gamePanel.player.armor
        for (itm in items) {
            if (i == gamePanel.player.itemNumChosen && gamePanel.player.itemNumChosen != null) {
                val add = 6
                if (armor != null && armor == itm) {
                    g.color = Color.green
                } else {
                    g.color = Color(0, 0, 0, 210)
                }
                g.fill3DRect((55 + i * 41) - add, 55 - add, 40 + add, 50 + add, true)
                g.drawImage(itm.getPicture(), (55 + i * 41) - add, 55 - add, 40 + add, 40 + add, null)
            } else {
                if (armor != null && armor == itm) {
                    g.color = Color.green
                } else {
                    g.color = Color(222, 222, 222)
                }
                g.fill3DRect(55 + i * 41, 55, 40, 40, true)
                g.drawImage(itm.getPicture(), 55 + i * 41, 55, 40, 40, null)
            }

            i += 1
        }
    }

    private fun fillPanel(g: Graphics2D) {
        g.color = Color.BLACK
        g.fill3DRect(0, 0, 800, 100,true)
        g.color = Color.lightGray
        g.fill3DRect(2, 2, 796, 96,true)
    }

    private fun painLvlOval(g: Graphics2D) {
        val lvl = gamePanel.player.lvl
        g.color = Color(0, 0, 0, 200)
        g.draw3DRect(5, 55, 40, 40, true)
        g.color = Color.BLACK
        g.font = Font("TimesRoman", Font.BOLD, 20)
        g.drawString(lvl.toString(), 20, 80)
    }

    private fun paintHpOrLvl(g: Graphics2D, v1: Int, v2: Int, lineColor: Color, offset: Int) {
        g.color = Color.WHITE
        g.fill3DRect(5, offset, 790, 20,true)
        g.color = lineColor
        g.fill3DRect(5, offset,
                (790 * (v1.toDouble() / v2)).toInt(), 20, true)

        g.font = Font("TimesRoman", Font.BOLD, 15)

        g.color = Color.DARK_GRAY
        g.drawString("[HP] $v1 / $v2", 400, offset + 15)
    }

}
