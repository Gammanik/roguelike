package ememies

import graphics.model.Character
import kotlin.math.absoluteValue

class AggressiveStrategy: BehaviourStrategy() {

    override fun behave(player: Character, mob: Mob) {
        if (player.getDistance(mob.xCoordinate, mob.yCoordinate) < 5) {
            goToPlayer(player, mob)
        }
    }

    private fun goToPlayer(p: Character, mob: Mob) {
        val deltaX = p.xCoordinate - mob.xCoordinate
        val deltaY = p.yCoordinate - mob.yCoordinate
        if (deltaX.absoluteValue < deltaY.absoluteValue) {
            // then move to y direction
            val firstTryResult: Boolean = if (deltaY > 0) {
                mob.stepDown()
            } else {
                mob.stepUp()
            }
            if (firstTryResult) return
            if (deltaX > 0) mob.stepRight()
            if (deltaX < 0) mob.stepLeft()
        } else {
            // then move to x direction
            val firstTryResult: Boolean = if (deltaX > 0) {
                mob.stepRight()
            } else {
                mob.stepLeft()
            }
            if (firstTryResult) return
            if (deltaY > 0) mob.stepUp()
            if (deltaY < 0) mob.stepDown()
        }
    }

    private fun mobAttacking() {

    }
}
