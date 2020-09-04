package com.roguelike.saving

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.GamePanel
import com.roguelike.enemies.Mob
import java.lang.reflect.Type

/** class to serialize [GamePanel] to Json format **/
class GamePanelSerializer : JsonSerializer<GamePanel> {
    override fun serialize(src: GamePanel, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.add("character", context.serialize(src.player))
        result.add("mobs", context.serialize(src.mobs))
        result.add("items", context.serialize(src.items))
        result.add("map", context.serialize(src.gameMap))
        return result
    }
}
