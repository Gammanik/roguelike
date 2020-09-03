package com.roguelike.saving

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.roguelike.GamePanel
import com.roguelike.items.*
import java.lang.reflect.Type

/** class to deserialize [ItemBase] from Json format **/
class ItemDeserializer: JsonDeserializer<ItemBase> {

    private fun deserializeStrategy(number: Int, x: Int, y: Int): ItemBase {
        return when (number) {
            1 -> AidItem(x, y)
            2 -> PoisonItem(x, y)
            3 -> PowerUpItem(x, y)
            4 -> ArmorItem(x, y)
            else -> AidItem(x, y)
        }
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ItemBase {
        val jsonObject: JsonObject = json.asJsonObject

        val type = jsonObject.get("type").asInt
        val x = jsonObject.get("x").asInt
        val y = jsonObject.get("y").asInt

        return deserializeStrategy(type, x, y)
    }
}
