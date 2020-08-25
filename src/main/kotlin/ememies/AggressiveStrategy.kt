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
                if ( !mob.stepDown()) {
                    if (deltaX > 0) mob.stepRight()
                    if (deltaX < 0) mob.stepLeft()
                }
            } else {
                if ( !mob.stepUp()) {
                    if (deltaX > 0) mob.stepRight()
                    if (deltaX < 0) mob.stepLeft()
                }
            }
        } else {
            // then move to x direction
            if (deltaX > 0) {
                if ( !mob.stepRight()) {
                    if (deltaY > 0) mob.stepUp()
                    if (deltaY < 0) mob.stepDown()
                }
            } else {
                if ( !mob.stepLeft()) {
                    if (deltaY > 0) mob.stepUp()
                    if (deltaY < 0) mob.stepDown()
                }
            }
        }
    }

    private fun mobAttacking() {

    }
}
