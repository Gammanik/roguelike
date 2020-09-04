package com.roguelike.commands

import com.roguelike.enemies.player.Character

/** switch the current item chosen to next one **/
class SwitchItemCommand(private val player: Character): Command() {
    override fun execute() {
        if (player.itemNumChosen == null) {
            player.itemNumChosen = 0
            return
        }

        player.itemNumChosen =
            (player.itemNumChosen!! + 1).rem(player.getCurrentItems().size)
    }
}
