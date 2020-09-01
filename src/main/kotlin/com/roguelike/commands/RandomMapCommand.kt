package com.roguelike.commands

import com.roguelike.graphics.GameMap

class RandomMapCommand(private var gameMap: GameMap?): Command() {
    override fun execute() {
        gameMap = GameMap()
    }
}
