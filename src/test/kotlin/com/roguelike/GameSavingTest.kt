package com.roguelike

import com.google.gson.GsonBuilder
import com.roguelike.enemies.Mob
import com.roguelike.enemies.behaviour.AggressiveStrategy
import com.roguelike.enemies.behaviour.FunkyStrategy
import com.roguelike.enemies.behaviour.PassiveStrategy
import com.roguelike.enemies.player.ConfusionSpellDecorator
import com.roguelike.enemies.player.Player
import com.roguelike.graphics.GameMap
import com.roguelike.items.*
import com.roguelike.saving.*
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GameSavingTest {

    @Test
    fun testMobs() {
        val mobs = mutableListOf<Mob>()
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
        val items = mutableListOf<ItemBase>()

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
            .registerTypeAdapter(GameMap::class.java, MapSerializer())
            .create()
        val json = gson.toJson(map)

        val gson2 = GsonBuilder()
            .registerTypeAdapter(GameMap::class.java, MapDeserializer())
            .create()
        val gameMap = gson2.fromJson(json, GameMap::class.java)

        assertEquals(map.getRectMap().size, gameMap.getRectMap().size)
        for (key in map.getRectMap().keys) {
            assertEquals(map.getRectMap()[key], gameMap.getRectMap()[key])
        }
    }

    @Test
    fun testGamePanelSave() {
        val panel = GamePanel(GameMap()) {}

        val gson = GsonBuilder()
            .registerTypeAdapter(com.roguelike.enemies.player.Character::class.java, PlayerSerializer())
            .registerTypeAdapter(Player::class.java, PlayerSerializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerSerializer())
            .registerTypeAdapter(Mob::class.java, MobSerializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelSerializer())
            .registerTypeAdapter(GameMap::class.java, MapSerializer())
            .registerTypeAdapter(ItemBase::class.java, ItemSerializer())
            .registerTypeAdapter(AidItem::class.java, ItemSerializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemSerializer())
                .registerTypeAdapter(ArmorItem::class.java, ItemSerializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemSerializer())
            .create()
        val json = gson.toJson(panel)

        val size = panel.mobs.size

        val gson2 = GsonBuilder()
            .registerTypeAdapter(Character::class.java, PlayerDeserializer())
            .registerTypeAdapter(Player::class.java, PlayerDeserializer())
            .registerTypeAdapter(ConfusionSpellDecorator::class.java, PlayerDeserializer())
            .registerTypeAdapter(Mob::class.java, MobDeserializer())
            .registerTypeAdapter(GamePanel::class.java, GamePanelDeserializer {})
            .registerTypeAdapter(GameMap::class.java, MapDeserializer())
            .registerTypeAdapter(ItemBase::class.java, ItemDeserializer())
            .registerTypeAdapter(AidItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PoisonItem::class.java, ItemDeserializer())
            .registerTypeAdapter(PowerUpItem::class.java, ItemDeserializer())
                .registerTypeAdapter(ArmorItem::class.java, ItemDeserializer())
            .create()
        val res = gson2.fromJson(json, GamePanel::class.java)

        assertEquals(panel.items.size, res.items.size)
        for (i in panel.items.indices) {
            assertEquals(panel.items[i].x, res.items[i].x)
            assertEquals(panel.items[i].y, res.items[i].y)
        }

        assertEquals(size, res.mobs.size)
        for (i in 0 until size) {
            assertEquals(panel.mobs[i].xCoordinate, res.mobs[i].xCoordinate)
            assertEquals(panel.mobs[i].yCoordinate, res.mobs[i].yCoordinate)
        }

        assertEquals(panel.player.xCoordinate, res.player.xCoordinate)
        assertEquals(panel.player.yCoordinate, res.player.yCoordinate)
    }

}
