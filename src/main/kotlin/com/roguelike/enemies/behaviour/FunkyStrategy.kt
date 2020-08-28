package com.roguelike.enemies.behaviour

import com.roguelike.enemies.Mob
import com.roguelike.enemies.player.Character
import com.roguelike.utils.MapChecker
import kotlin.math.absoluteValue

/** class for funky mob strategy */
class FunkyStrategy: BehaviourStrategy() {
    override fun behave(player: Character, mob: Mob, checker: MapChecker) {
        if (player.getDistance(mob.xCoordinate, mob.yCoordinate) < 10) {
            goFromPlayer(player, mob, checker)
        }
    }

    private fun goFromPlayer(p: Character, mob: Mob, checker: MapChecker) {
        val deltaX = p.xCoordinate - mob.xCoordinate
        val deltaY = p.yCoordinate - mob.yCoordinate

        if (deltaX <= 1 && deltaY <= 1) {
            doAnyStep(mob, checker)
        }

        if (deltaX.absoluteValue < deltaY.absoluteValue) {
            // then move to y direction
            val firstTryResult: Boolean = if (deltaY > 0) {
                mob.stepUp(checker)
            } else {
                mob.stepDown(checker)
            }
            if (firstTryResult) return
            if (deltaX > 0) mob.stepLeft(checker)
            if (deltaX < 0) mob.stepRight(checker)
        } else {
            // then move to x direction
            val firstTryResult: Boolean = if (deltaX > 0) {
                mob.stepLeft(checker)
            } else {
                mob.stepRight(checker)
            }
            if (firstTryResult) return
            if (deltaY < 0) mob.stepDown(checker)
            if (deltaY > 0) mob.stepUp(checker)
        }
    }

    private fun doAnyStep(mob: Mob, checker: MapChecker) {
        if (mob.stepRight(checker)) return
        if (mob.stepLeft(checker)) return
        if (mob.stepDown(checker)) return
        if (mob.stepUp(checker)) return
    }
}