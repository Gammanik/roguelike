package com.roguelike.saving

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.roguelike.graphics.GameMap
import com.roguelike.graphics.model.MapPoint
import com.roguelike.items.ItemBase
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap


class MapDeserializer: JsonDeserializer<GameMap> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): GameMap {
//        val jsonObject: JsonObject = json.asJsonObject
//
//        val setType = object : TypeToken<HashSet<Pair<Int, Int>>>() {}.type
//
//        val wallSet = context.deserialize<HashSet<Pair<Int, Int>>>(
//            jsonObject.get("wallSet"),
//            setType
//        )!!.toMutableSet()
//
//        val setType2 = object : TypeToken<HashMap<Pair<Int, Int>, MapPoint>>() {}.type
//
//        val rectMap = context.deserialize<HashMap<Pair<Int, Int>, MapPoint>>(
//            jsonObject.get("rectMap"),
//            setType2
//        ).toMutableMap()
//
//        return GameMap(wallSet, rectMap)

        val jsonObject: JsonObject = json.asJsonObject

        val setType = object : TypeToken<Array<Pair<Int, Int>>>() {}.type
        val wallSetList = context.deserialize<Array<Pair<Int, Int>>>(jsonObject.get("wallSet"),
                setType)!!

        val setType2 = object : TypeToken<List<MapPoint>>() {}.type
        val rectMapList = context.deserialize<List<MapPoint>>(jsonObject.get("rectMap"),
                setType2)!!

        val wallSet = wallSetList.toMutableSet()
        val rectMap = mutableMapOf<Pair<Int, Int>, MapPoint>()

        rectMapList.forEach {
            p -> rectMap[Pair(p.x, p.y)] = p
        }

        return GameMap(wallSet, rectMap)
    }
}

//result.add("character", context.serialize(src.player))
//result.add("mobs", context.serialize(src.mobs))
//result.add("items", context.serialize(src.items))
//result.add("map", context.serialize(src.gameMap))