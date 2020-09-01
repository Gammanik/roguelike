package com.roguelike.saving

import com.google.gson.*
import com.roguelike.enemies.Mob
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.BehaviourStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.Player
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import java.lang.reflect.Type


class PlayerDeserializer : JsonDeserializer<Character> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Character {
        val jsonObject: JsonObject = json.asJsonObject

        val xCoordinate = jsonObject.get("x").asInt
        val yCoordinate = jsonObject.get("y").asInt
        val hp = jsonObject.get("hp").asInt
        val lvl = jsonObject.get("lvl").asInt
        val exp = jsonObject.get("exp").asInt
        val expMax = jsonObject.get("expMax").asInt

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ItemBase::class.java, ItemSerializer())
            .create()
        val items : MutableList<ItemBase> = gson.fromJson(jsonObject.get("items"), MutableList::class.java) as
                MutableList<ItemBase>

        return Player(
            xCoordinate,
            yCoordinate,
            hp,
            lvl,
            exp,
            expMax,
            items
        )
    }
}