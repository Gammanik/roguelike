package ememies

import graphics.model.Character
import graphics.model.MapChecker
import kotlin.math.absoluteValue

class AggressiveStrategy: BehaviourStrategy() {

    override fun behave(player: Character, mob: Mob, checker: MapChecker) {
        if (player.getDistance(mob.xCoordinate, mob.yCoordinate) < 19) {
            goToPlayer(player, mob, checker)
        }
    }

    private fun goToPlayer(p: Character, mob: Mob, checker: MapChecker) {
        val deltaX = p.xCoordinate - mob.xCoordinate
        val deltaY = p.yCoordinate - mob.yCoordinate
        if (deltaX.absoluteValue < deltaY.absoluteValue) {
            // then move to y direction
            val firstTryResult: Boolean = if (deltaY > 0) {
                mob.stepDown(checker)
            } else if (deltaY < 0) {
                mob.stepUp(checker)
            } else false

            if (firstTryResult) return

            if (deltaX > 0) mob.stepRight(checker)
            if (deltaX < 0) mob.stepLeft(checker)
        } else {
            // then move to x direction
            val firstTryResult: Boolean = if (deltaX > 0) {
                mob.stepRight(checker)
            } else if (deltaX < 0) {
                mob.stepLeft(checker)
            } else false

            if (firstTryResult) return

            if (deltaY > 0) mob.stepDown(checker)
            if (deltaY < 0) mob.stepUp(checker)
        }
    }

    private fun mobAttacking() {

    }
}
