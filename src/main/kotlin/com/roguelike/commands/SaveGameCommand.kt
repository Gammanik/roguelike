package com.roguelike.commands

import GamePanel
import com.google.gson.GsonBuilder
import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import com.roguelike.saving.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class SaveGameCommand(private val gamePanel: GamePanel): Command() {
    override fun execute() {
        val file = File("src/main/resources/snapshots", "snapshot")
        file.createNewFile()
        val writer = BufferedWriter(FileWriter(file));
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Character::class.java, PlayerSerializer())
            .registerTypeAdapter(Player::class.java, PlayerSerializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerSerializer())
            .registerTypeAdapter(Mob::class.java, MobSerializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelSerializer())
            .registerTypeAdapter(GameMap::class.java, MapSerializer())
            .registerTypeAdapter(ItemBase::class.java, ItemSerializer())
            .registerTypeAdapter(AidItem::class.java, ItemSerializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemSerializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemSerializer())
            .create()

        val json = gson.toJson(gamePanel)
        writer.write(json)
        writer.close()
    }
}
