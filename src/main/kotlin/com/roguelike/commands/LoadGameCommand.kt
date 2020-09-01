package com.roguelike.commands

import GamePanel
import com.google.gson.GsonBuilder
import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import com.roguelike.saving.*
import java.io.File
import java.nio.file.Files

class LoadGameCommand(private val playerDeadCb: () -> Unit): Command() {
    override fun execute(): GamePanel {
        val file = File("src/main/resources/snapshots", "snapshot")
        val content = String(Files.readAllBytes(file.toPath()))
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Character::class.java, PlayerDeserializer())
            .registerTypeAdapter(Player::class.java, PlayerDeserializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerDeserializer())
            .registerTypeAdapter(Mob::class.java, MobDeserializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelDeserializer(playerDeadCb))
            .registerTypeAdapter(GameMap::class.java, MapDeserializer())
            .registerTypeAdapter(ItemBase::class.java, ItemDeserializer())
            .registerTypeAdapter(AidItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemDeserializer())
            .create()

        return gson.fromJson(content, GamePanel::class.java)
    }
}
