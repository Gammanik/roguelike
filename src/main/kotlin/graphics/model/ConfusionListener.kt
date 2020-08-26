package graphics.model

import GamePanel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class ConfusionListener(private val gamePanel: GamePanel) : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        gamePanel.endConfusion()
    }

}