package com.roguelike.enemies.player

import com.roguelike.utils.MapChecker
import kotlin.random.Random

/** decorator for confusion spell to main character */
class ConfusionSpellDecorator(pl: Character) : PlayerDecorator(pl) {
    override fun stepLeft(checker: MapChecker) : Boolean {
        return confusedStep(0, checker)
    }

    override fun stepRight(checker: MapChecker) : Boolean {
        return confusedStep(2, checker)
    }

    override fun stepUp(checker: MapChecker) : Boolean {
        return confusedStep(1, checker)
    }

    override fun stepDown(checker: MapChecker) : Boolean {
        return confusedStep(3, checker)
    }

    private fun confusedStep(funcNumber: Int, checker: MapChecker) : Boolean {
        return randomHelperFunc((funcNumber + generateDelta()) % 4, checker)
    }

    private fun generateDelta(): Int {
        return Random.nextInt(3) - 1
    }

    private fun randomHelperFunc(number: Int, checker: MapChecker) : Boolean {
        return when (number) {
            0 -> player.stepLeft(checker)
            1 -> player.stepUp(checker)
            2 -> player.stepRight(checker)
            3 -> player.stepDown(checker)
            else -> false
        }
    }
}
