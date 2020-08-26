package ememies

import graphics.model.Character
import graphics.model.MapChecker
import graphics.model.Player
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class MobListener(private val checker: MapChecker, private val player: Character) : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        for (mob in checker.getClosestMobs()) {
            mob.attackPlayer(player)
        }
    }
}