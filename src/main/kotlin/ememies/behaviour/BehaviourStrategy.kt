package ememies.behaviour

import ememies.Mob
import player.Character
import utils.MapChecker

abstract class BehaviourStrategy {
    open fun behave(player: Character, mob: Mob, checker: MapChecker) { }
}
