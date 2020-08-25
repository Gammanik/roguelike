package ememies

import graphics.model.Character

abstract class BehaviourStrategy {
    open fun behave(player: Character, mob: Mob) { }
}
