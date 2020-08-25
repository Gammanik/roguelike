package ememies

import graphics.model.Character
import kotlin.math.absoluteValue

class FunkyStrategy: BehaviourStrategy() {
    override fun behave(player: Character, mob: Mob) {
        if (player.getDistance(mob.xCoordinate, mob.yCoordinate) < 10) {
            goFromPlayer(player, mob)
        }
    }

    // todo: generalize
    private fun goFromPlayer(p: Character, mob: Mob) {
        val deltaX = p.xCoordinate - mob.xCoordinate
        val deltaY = p.yCoordinate - mob.yCoordinate
        if (deltaX.absoluteValue < deltaY.absoluteValue) {
            // then move to y direction
            val firstTryResult: Boolean = if (deltaY > 0) {
                mob.stepUp()
            } else {
                mob.stepDown()
            }
            if (firstTryResult) return
            if (deltaX > 0) mob.stepLeft()
            if (deltaX < 0) mob.stepRight()
        } else {
            // then move to x direction
            val firstTryResult: Boolean = if (deltaX > 0) {
                mob.stepLeft()
            } else {
                mob.stepRight()
            }
            if (firstTryResult) return
            if (deltaY > 0) mob.stepDown()
            if (deltaY < 0) mob.stepUp()
        }
    }
}