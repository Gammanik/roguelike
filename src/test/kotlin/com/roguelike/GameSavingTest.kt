package com.roguelike

import GamePanel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.roguelike.enemies.Mob
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.graphics.GameMap
import com.roguelike.graphics.map_loading.BadMapFileException
import com.roguelike.items.AidItem
import com.roguelike.items.ItemBase
import com.roguelike.items.PoisonItem
import com.roguelike.items.PowerUpItem
import com.roguelike.saving.*
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Test
import java.io.File

class GameSavingTest {

    @Test
    fun testMobs() {
        var mobs = mutableListOf<Mob>()
        val aggressiveStrategy = AggressiveStrategy()
        val funkyStrategy = FunkyStrategy()
        val passiveStrategy = PassiveStrategy()

        mobs.add(Mob(10, 10, aggressiveStrategy))
        mobs.add(Mob(30, 8, funkyStrategy))
        mobs.add(Mob(25, 17, aggressiveStrategy))
        mobs.add(Mob(17, 20, aggressiveStrategy))
        mobs.add(Mob(2, 2, passiveStrategy))
        mobs.add(Mob(27, 27, aggressiveStrategy))
        mobs.add(Mob(30, 35, aggressiveStrategy))
        mobs.add(Mob(32, 40, aggressiveStrategy))
        mobs.add(Mob(32, 45, aggressiveStrategy))

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Mob::class.java, MobSerializer())
            .create()
        val json = gson.toJson(mobs)

        val gson2 = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Mob::class.java, MobDeserializer())
            .create()

        val mobsList = gson2.fromJson(json, Array<Mob>::class.java).toList()

        assertEquals(mobs.size, mobsList.size)

        for (i in mobsList.indices) {
            assertEquals(mobs[i].x, mobsList[i].x)
            assertEquals(mobs[i].y, mobsList[i].y)
            assertEquals(mobs[i].hp, mobsList[i].hp)
        }
    }

    @Test
    fun testItems() {
        var items = mutableListOf<ItemBase>()

        items.add(AidItem(2, 20))
        items.add(AidItem(5, 35))
        items.add(AidItem(4, 15))
        items.add(PowerUpItem(5, 5))
        items.add(PowerUpItem(10, 10))
        items.add(PowerUpItem(10, 15))
        items.add(PoisonItem(40, 15))

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ItemBase::class.java, ItemSerializer())
            .registerTypeAdapter(AidItem::class.java, ItemSerializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemSerializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemSerializer())
            .create()
        val json = gson.toJson(items)

        val gson2 = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ItemBase::class.java, ItemDeserializer())
            .registerTypeAdapter(AidItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemDeserializer())
            .create()

        val itemsList = gson2.fromJson(json, Array<ItemBase>::class.java).toList()

        assertEquals(items.size, itemsList.size)

        for (i in items.indices) {
            assertEquals(items[i].x, itemsList[i].x)
            assertEquals(items[i].y, itemsList[i].y)
        }
    }

    @Test
    fun testMap() {
        val map = GameMap()

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(GameMap::class.java, MapSerializer())
            .create()

        val json = gson.toJson(map)

//        println(json)

        val gson2 = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(GameMap::class.java, MapDeserializer())
            .create()

        val gameMap = gson2.fromJson(json, GameMap::class.java)

        assertEquals(map.getRectMap().size, gameMap.getRectMap().size)

//        for (key in map.getRectMap().keys) {
//            assertEquals(map.getRectMap()[key], gameMap.getRectMap()[key])
//        }
    }

}