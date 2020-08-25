package graphics.model

import kotlin.random.Random

class ConfusionSpellDecorator(pl: Character, checker: MapChecker) : PlayerDecorator(pl, checker) {
    override fun stepLeft() : Boolean {
        return confusedStep(0)
    }

    override fun stepRight() : Boolean {
        return confusedStep(2)
    }

    override fun stepUp() : Boolean {
        return confusedStep(1)
    }

    override fun stepDown() : Boolean {
        return confusedStep(3)
    }

    private fun confusedStep(funcNumber: Int) : Boolean {
        return randomHelperFunc((funcNumber + generateDelta()) % 4)
    }

    private fun generateDelta(): Int {
        return Random.nextInt(3) - 1
    }

    private fun randomHelperFunc(number: Int) : Boolean {
        return when (number) {
            0 -> player.stepLeft()
            1 -> player.stepUp()
            2 -> player.stepRight()
            3 -> player.stepDown()
            else -> false
        }
    }
}