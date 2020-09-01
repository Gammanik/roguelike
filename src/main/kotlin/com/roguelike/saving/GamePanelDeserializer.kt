package com.roguelike.saving

import GamePanel
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import java.lang.reflect.Type

class GamePanelDeserializer(val playerDeadCallback: () -> Unit): JsonDeserializer<GamePanel> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): GamePanel {
        val jsonObject: JsonObject = json.asJsonObject

        val character = context.deserialize<Player>(jsonObject.get("character"), Player::class.java)!!
        val mobs = context.deserialize<Array<Mob>>(jsonObject.get("mobs"), Array<Mob>::class.java)!!.toMutableList()

        val items = context.deserialize<Array<ItemBase>>(jsonObject.get("items"),
            Array<ItemBase>::class.java)!!.toMutableList()

        val gameMap: GameMap = context.deserialize(jsonObject.get("map"), GameMap::class.java)
//        val gameMap: GameMap = GameMap()
        return GamePanel(gameMap, mobs, character, items, playerDeadCallback)
    }
}

//result.add("character", context.serialize(src.player))
//result.add("mobs", context.serialize(src.mobs))
//result.add("items", context.serialize(src.items))
//result.add("map", context.serialize(src.gameMap))