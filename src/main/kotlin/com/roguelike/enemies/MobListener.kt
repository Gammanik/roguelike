package com.roguelike.enemies

import com.roguelike.enemies.player.Character
import com.roguelike.utils.MapChecker
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/** mob listener for attack player on timer ticks */
class MobListener(private val checker: MapChecker, private val player: Character) : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        for (mob in checker.getClosestMobs()) {
            mob.attackPlayer(player)
        }
    }
}
