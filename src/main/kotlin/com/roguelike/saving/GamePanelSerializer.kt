package com.roguelike.saving

import GamePanel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.enemies.player.Character
import java.lang.reflect.Type

class GamePanelSerializer : JsonSerializer<GamePanel> {
    override fun serialize(src: GamePanel, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.add("character", context.serialize(src.player))
        result.add("mobs", context.serialize(src.mobs))
        result.add("map", context.serialize(src.gameMap))
        return result
    }
}