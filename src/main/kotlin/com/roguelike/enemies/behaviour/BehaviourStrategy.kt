package com.roguelike.enemies.behaviour

import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.Character
import com.roguelike.utils.MapChecker

/** abstract class for mob moving behaviour */
abstract class BehaviourStrategy {
    /** define mob behave */
    open fun behave(player: Character, mob: Mob, checker: MapChecker) { }
}
