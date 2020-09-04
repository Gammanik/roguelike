package com.roguelike.commands

import com.roguelike.enemies.player.Character

/** drop the current chosen item **/
class DropItemCommand(private val player: Character): Command() {
    override fun execute() {
        val itemNum = player.itemNumChosen
        if (itemNum != null) {
            val itemToDelete = player.getCurrentItems()[itemNum]
            player.deleteItem(itemToDelete)
        }
    }
}
