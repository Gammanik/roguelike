package com.roguelike.ememies.behaviour

import com.roguelike.ememies.Mob
import com.roguelike.ememies.player.Character
import com.roguelike.utils.MapChecker

/** abstract class for mob moving behaviour */
abstract class BehaviourStrategy {
    /** define mob behave */
    open fun behave(player: Character, mob: Mob, checker: MapChecker) { }
}
