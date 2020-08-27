package com.roguelike.ememies.behaviour

import com.roguelike.ememies.Mob
import com.roguelike.ememies.player.Character
import com.roguelike.utils.MapChecker

abstract class BehaviourStrategy {
    open fun behave(player: Character, mob: Mob, checker: MapChecker) { }
}
