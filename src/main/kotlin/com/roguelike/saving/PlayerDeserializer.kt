package com.roguelike.saving

import com.google.gson.*
import com.roguelike.enemies.player.Character
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.items.ItemBase
import java.lang.reflect.Type

/** class to deserialize [Character] from Json format with gson lib **/
class PlayerDeserializer : JsonDeserializer<Character> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Character {
        val jsonObject: JsonObject = json.asJsonObject

        val xCoordinate = jsonObject.get("x").asInt
        val yCoordinate = jsonObject.get("y").asInt
        val hp = jsonObject.get("hp").asInt
        val lvl = jsonObject.get("lvl").asInt
        val exp = jsonObject.get("exp").asInt
        val expMax = jsonObject.get("expMax").asInt

        val items = context.deserialize<Array<ItemBase>>(jsonObject.get("items"),
                Array<ItemBase>::class.java)!!.toMutableList()

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
