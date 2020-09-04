package com.roguelike.saving

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.graphics.GameMap
import java.lang.reflect.Type

/** class to serialize [GameMap] to Json format **/
class MapSerializer : JsonSerializer<GameMap> {
    override fun serialize(src: GameMap, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.add("wallSet", context.serialize(src.wallSet.toList()))
        result.add("rectMap", context.serialize(src.getRectMap().values.toList()))
        return result
    }
}
