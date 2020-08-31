
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JPanel
import javax.swing.Timer


class InfoMenuPanel(private val gamePanel: GamePanel) : JPanel(), ActionListener {

    private val timer: Timer = Timer(100, this)

    init {
        timer.start()
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
        g.drawString("[HP] $hp / 100", 400, 20);

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

    }

    override fun getPreferredSize() : Dimension {
        return Dimension(800, 100)
    }

}