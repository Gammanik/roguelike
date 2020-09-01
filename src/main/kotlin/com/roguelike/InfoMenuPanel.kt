package com.roguelike
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

    init {
        addBindings()
        timer.start()
    }

    private fun addBindings() {
        for (i in 1..Settings.MAX_ITEMS) {
            bindKey(i.toString()) { gamePanel.player.itemNumChosen = i - 1 }
        }

        bindKey(Keys.KEY_EXECUTE) {
            val itemNum = gamePanel.player.itemNumChosen
            if (itemNum != null) {
                val itemToExecute = gamePanel.player.getCurrentItems()[itemNum]
                itemToExecute.execute(gamePanel.player)
                gamePanel.player.deleteItem(itemToExecute)
            }
        }

        bindKey(Keys.DROP) {
            val itemNum = gamePanel.player.itemNumChosen
            if (itemNum != null) {
                val itemToDelete = gamePanel.player.getCurrentItems()[itemNum]
                gamePanel.player.deleteItem(itemToDelete)
            }
        }

        bindKey(Keys.SWITCH_ITEM) {
            if (gamePanel.player.itemNumChosen == null) {
                gamePanel.player.itemNumChosen = 0
                return@bindKey
            }

            gamePanel.player.itemNumChosen =
                    (gamePanel.player.itemNumChosen!! + 1).rem(gamePanel.player.getCurrentItems().size)
        }

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
        for (itm in items) {
            g.color = if (i == gamePanel.player.itemNumChosen && gamePanel.player.itemNumChosen != null)
                Color(22, 22, 22)
                else Color(222, 222, 222)

            g.fill3DRect(55 + i * 41, 55, 40, 40, true)
            g.drawImage(itm.getPicture(), 55 + i * 41, 55, 40, 40, null)
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
        g.color = Color.MAGENTA
        g.fillOval(5, 55, 40, 40)

        g.color = Color.DARK_GRAY
        g.font = Font("TimesRoman", Font.BOLD, 20)
        g.drawString(lvl.toString(), 20, 80)
    }

    private fun paintHpOrLvl(g: Graphics2D, v1: Int, v2: Int, lineColor: Color, offset: Int) {
        g.color = Color.WHITE
        g.fill3DRect(5, offset, 790, 20,true)
        g.color = lineColor
        g.fill3DRect(5, offset,
                (790 * (v1.toDouble() / v2)).toInt(), 20,true)

        g.font = Font("TimesRoman", Font.BOLD, 15)

        g.color = Color.DARK_GRAY
        g.drawString("[HP] $v1 / $v2", 400, offset + 15)
    }

}
