package ememies

import graphics.model.Character
import kotlin.math.absoluteValue

class AggressiveStrategy: BehaviourStrategy() {

    override fun behave(player: Character, mob: Mob) {
        goToPlayer(player, mob)
    }

    private fun goToPlayer(p: Character, mob: Mob) {
        val deltaX = p.xCoordinate - mob.xCoordinate
        val deltaY = p.yCoordinate - mob.yCoordinate

        if (deltaX.absoluteValue < deltaY.absoluteValue) {
            // then move to y direction
            if (deltaY > 0) {
                // todo: if step is false then try other direction
//                if (!mob.stepDown()) {
//                    return
//                }
            } else {
                mob.stepUp()
            }
        } else {
            // then move to x direction
            if (deltaX > 0) {
                mob.stepRight()
            } else {
                mob.stepLeft()
            }
        }
    }

    private fun mobAttacking() {

    }
}
