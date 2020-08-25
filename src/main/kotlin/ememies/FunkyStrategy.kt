package ememies

import graphics.model.Character
import kotlin.math.absoluteValue

class FunkyStrategy: BehaviourStrategy() {
    override fun behave(player: Character, mob: Mob) {
        goFromPlayer(player, mob)
    }

    // todo: generalize
    private fun goFromPlayer(p: Character, mob: Mob) {
        val deltaX = p.xCoordinate - mob.xCoordinate
        val deltaY = p.yCoordinate - mob.yCoordinate

        if (deltaX.absoluteValue < deltaY.absoluteValue) {
            // then move to y direction
            if (deltaY > 0) {
                // todo: if step is false then try other direction
                mob.stepUp()
            } else {
                mob.stepDown()
            }
        } else {
            if (deltaX > 0) {
                mob.stepLeft()
            } else {
                mob.stepDown()
            }
        }
    }
}