package ememies

import player.Character
import utils.MapChecker
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class MobListener(private val checker: MapChecker, private val player: Character) : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        for (mob in checker.getClosestMobs()) {
            mob.attackPlayer(player)
        }
    }
}
