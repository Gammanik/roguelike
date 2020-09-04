package com.roguelike.commands

import com.roguelike.enemies.player.Character

/** set the current item chosen to num **/
class ChooseItemNumCommand(private val player: Character, private val num: Int): Command() {
    override fun execute() {
        player.itemNumChosen = num
    }
}
