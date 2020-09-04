package com.roguelike.saving

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.roguelike.enemies.Mob
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.BehaviourStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import java.lang.reflect.Type

/** class to serialize [Mob] to Json format **/
class MobSerializer : JsonSerializer<Mob> {

    private fun serializeStrategy(strategy: BehaviourStrategy): Int {
        return when (strategy) {
            is AggressiveStrategy -> 1
            is FunkyStrategy -> 2
            is PassiveStrategy -> 3
            else -> 0
        }
    }

    override fun serialize(src: Mob, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val result = JsonObject()
        result.addProperty("x", src.xCoordinate)
        result.addProperty("y", src.yCoordinate)
        result.addProperty("hp", src.hp)
        result.addProperty("behaviour", serializeStrategy(src.currentBehavior))
        return result
    }
}
