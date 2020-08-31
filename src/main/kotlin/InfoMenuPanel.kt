
import com.roguelike.utils.Settings
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*


class InfoMenuPanel(private val gamePanel: GamePanel) : JPanel(), ActionListener {

    private val timer: Timer = Timer(100, this)

    init {
        addBindings()
        timer.start()
    }

    private fun addBindings() {
        for (i in 1..Settings.MAX_ITEMS) {
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i.toString()), i.toString())
            this.actionMap.put(i.toString(), object : AbstractAction() {
                override fun actionPerformed(p0: ActionEvent?) {
                    gamePanel.player.itemNumChosen = i - 1
                }
            })
        }

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("E"), "E")
        this.actionMap.put("E", object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) {
                val itemNum = gamePanel.player.itemNumChosen
                if (itemNum != null) {
                    val itemToExecute = gamePanel.player.getCurrentItems()[itemNum]
                    itemToExecute.execute(gamePanel.player)
                    gamePanel.player.deleteItem(itemToExecute)
                }
            }
        })

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "F")
        this.actionMap.put("F", object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) {
                val itemNum = gamePanel.player.itemNumChosen
                if (itemNum != null) {
                    val itemToDelete = gamePanel.player.getCurrentItems()[itemNum]
                    gamePanel.player.deleteItem(itemToDelete)
                }
            }
        })


        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"), "Q")
        this.actionMap.put("Q", object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) {
                if (gamePanel.player.itemNumChosen == null) {
                    gamePanel.player.itemNumChosen = 0
                    return
                }

                gamePanel.player.itemNumChosen =
                        (gamePanel.player.itemNumChosen!! + 1).rem(gamePanel.player.getCurrentItems().size)
            }
        })
    }

    override fun actionPerformed(e: ActionEvent?) {
        this.repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val gameField = g as Graphics2D

        // lets paint frame
        gameField.color = Color.BLACK
        gameField.fill3DRect(0, 0,
                800, 100,true)

        gameField.color = Color.lightGray
        gameField.fill3DRect(2, 2,
                796, 96,true)

        // lets paint hp
        val hp = gamePanel.player.hp

        gameField.color = Color.WHITE
        gameField.fill3DRect(5, 5,
                790, 20,true)

        gameField.color = Color.RED
        gameField.fill3DRect(5, 5,
                (790 * (hp.toDouble() / 100)).toInt(), 20,true)

        g.setFont(Font("TimesRoman", Font.BOLD, 15))

        gameField.color = Color.DARK_GRAY
        g.drawString("[HP] $hp / 100", 400, 20)

        // lets paint lvl
        val exp = gamePanel.player.exp
        val lvl = gamePanel.player.lvl
        val expMax = gamePanel.player.expMax

        gameField.color = Color.WHITE
        gameField.fill3DRect(5, 30,
                790, 20,true)

        gameField.color = Color.YELLOW
        gameField.fill3DRect(5, 30,
                (790 * (exp.toDouble() / expMax)).toInt(), 20,true)

        gameField.color = Color.DARK_GRAY
        g.drawString("[EXP] $exp / $expMax", 400, 45);

        // lets paint lvl oval
        gameField.color = Color.MAGENTA
        g.fillOval(5, 55, 40, 40)

        gameField.color = Color.DARK_GRAY
        g.setFont(Font("TimesRoman", Font.BOLD, 20))
        g.drawString(lvl.toString(), 20, 80)

        drawCurrentItems(gameField)
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

}
