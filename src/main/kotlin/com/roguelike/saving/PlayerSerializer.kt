package com.roguelike.saving

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.enemies.player.Character
import java.lang.reflect.Type

/** class for player serialization to Json format **/
class PlayerSerializer : JsonSerializer<Character> {
    override fun serialize(src: Character, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.addProperty("x", src.xCoordinate)
        result.addProperty("y", src.yCoordinate)
        result.addProperty("hp", src.hp)
        result.addProperty("lvl", src.lvl)
        result.addProperty("exp", src.exp)
        result.addProperty("expMax", src.expMax)
        result.add("items", context.serialize(src.getCurrentItems()))
        return result
    }
}