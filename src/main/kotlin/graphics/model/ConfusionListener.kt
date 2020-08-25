package graphics.model

import GamePanel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class ConfusionListener(val gamePanel: GamePanel) : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        gamePanel.endConfusion()
    }

}