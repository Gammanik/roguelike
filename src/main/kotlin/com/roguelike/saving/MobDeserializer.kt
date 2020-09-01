package com.roguelike.saving

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.roguelike.enemies.Mob
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.BehaviourStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import java.lang.reflect.Type

class MobDeserializer: JsonDeserializer<Mob> {

    private fun deserializeStrategy(number: Int): BehaviourStrategy {
        return when (number) {
            1 -> AggressiveStrategy()
            2 -> FunkyStrategy()
            3 -> PassiveStrategy()
            else -> AggressiveStrategy()
        }
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Mob {
        val jsonObject: JsonObject = json.asJsonObject

        val xCoordinate = jsonObject.get("x").asInt
        val yCoordinate = jsonObject.get("y").asInt
        val hp = 100
        val behaviourStrategy = jsonObject.get("behaviour").asInt

        return Mob(xCoordinate, yCoordinate, hp, deserializeStrategy(behaviourStrategy))
    }
}