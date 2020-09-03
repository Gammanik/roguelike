package com.roguelike.commands


import com.google.gson.GsonBuilder
import com.roguelike.GamePanel
import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.graphics.map_loading.LoadMapMenu
import com.roguelike.items.*
import com.roguelike.saving.*
import java.io.File
import java.nio.file.Files

/** command for loading last saved game **/
class LoadGameCommand(private val menu: LoadMapMenu): Command() {
    override fun execute() {
        val file = File("src/main/resources/snapshots", "snapshot")
        val content = String(Files.readAllBytes(file.toPath()))
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Character::class.java, PlayerDeserializer())
            .registerTypeAdapter(Player::class.java, PlayerDeserializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerDeserializer())
            .registerTypeAdapter(Mob::class.java, MobDeserializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelDeserializer(menu.playerDeadCb))
            .registerTypeAdapter(GameMap::class.java, MapDeserializer())
            .registerTypeAdapter(ItemBase::class.java, ItemDeserializer())
            .registerTypeAdapter(AidItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemDeserializer())
            .registerTypeAdapter(ArmorItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemDeserializer())
            .create()


        menu.setGamePanel(gson.fromJson(content, GamePanel::class.java))
    }
}
