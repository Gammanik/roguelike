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
import com.roguelike.saving.MobDeserializer
import com.roguelike.saving.MobSerializer
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Test
import java.io.File

class GameSavingTest {
    @Test
    fun testCreateMap() {
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

}