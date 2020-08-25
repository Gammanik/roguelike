package ememies

import graphics.model.Character

class AggressiveBehaviourStrategy: BehaviourStrategy() {

    override fun behave(player: Character, mob: Mob) {
        mob.currY += 1
        mob.currX += 1

    }

    private fun mobAttacking() {

    }
}
