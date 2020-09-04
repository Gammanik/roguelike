package com.roguelike.commands

import com.roguelike.enemies.player.Character

/** apply the current item **/
class ExecuteItemCommand(private val player: Character): Command() {
    override fun execute() {
        val itemNum = player.itemNumChosen
        val itemsCount = player.getCurrentItems().size
        if (itemNum != null && itemNum < itemsCount) {
            val itemToExecute = player.getCurrentItems()[itemNum]
            itemToExecute.execute(player)
            if (itemToExecute.isUsable) {
                player.deleteItem(itemToExecute)
            }
        }
    }
}
