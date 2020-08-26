package ememies

import graphics.model.Character
import graphics.model.MapChecker

abstract class BehaviourStrategy {
    open fun behave(player: Character, mob: Mob, checker: MapChecker) { }
}
