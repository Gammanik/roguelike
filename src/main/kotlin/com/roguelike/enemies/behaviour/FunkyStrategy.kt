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
        val firstTryResult: Boolean
        val secondTryResult: Boolean

        if (deltaX.absoluteValue > deltaY.absoluteValue) {
            // then move to y direction
            firstTryResult = when {
                deltaY > 0 -> mob.stepUp(checker)
                deltaY < 0 -> mob.stepDown(checker)
                else -> false
            }

            if (firstTryResult) return

            secondTryResult = if (deltaX > 0) {
                mob.stepLeft(checker)
            } else {
                mob.stepRight(checker)
            }
        } else {
            // then move to x direction
            firstTryResult = when {
                deltaX > 0 -> mob.stepLeft(checker)
                deltaX < 0 -> mob.stepRight(checker)
                else -> false
            }

            if (firstTryResult) return

            secondTryResult = if (deltaY < 0) {
                mob.stepDown(checker)
            } else {
                mob.stepUp(checker)
            }
        }

        if ( !firstTryResult && !secondTryResult && deltaX.absoluteValue < 2 && deltaY.absoluteValue < 2) {
            doAnyStep(mob, checker)
        }
    }

    private fun doAnyStep(mob: Mob, checker: MapChecker) {
        if (mob.stepRight(checker)) return
        if (mob.stepLeft(checker)) return
        if (mob.stepDown(checker)) return
        if (mob.stepUp(checker)) return
    }
}