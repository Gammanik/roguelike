package com.roguelike.saving

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.enemies.Mob
import com.roguelike.items.*
import java.lang.reflect.Type

/** class to serialize [ItemBase] to Json format **/
class ItemSerializer : JsonSerializer<ItemBase> {

    private fun serializeStrategy(itemBase: ItemBase): Int {
        return when (itemBase) {
            is AidItem -> 1
            is PoisonItem -> 2
            is PowerUpItem -> 3
            is ArmorItem -> 4
            else -> 0
        }
    }

    override fun serialize(src: ItemBase, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.addProperty("x", src.x)
        result.addProperty("y", src.y)
        result.addProperty("type", serializeStrategy(src))
        return result
    }
}
