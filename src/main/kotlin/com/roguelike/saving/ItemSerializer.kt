package com.roguelike.saving
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import java.lang.reflect.Type

class ItemSerializer : JsonSerializer<ItemBase> {

    private fun serializeStrategy(itemBase: ItemBase): Int {
        return when (itemBase) {
            is AidItem -> 1
            is PoisonItem -> 2
            is PowerUpItem -> 3
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