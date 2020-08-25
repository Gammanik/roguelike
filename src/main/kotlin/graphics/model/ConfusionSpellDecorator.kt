package graphics.model

import kotlin.random.Random

class ConfusionSpellDecorator(pl: Character, checker: MapChecker) : PlayerDecorator(pl, checker) {
    override fun stepLeft() {
        confusedStep(0)
    }

    override fun stepRight() {
        confusedStep(2)
    }

    override fun stepUp() {
        confusedStep(1)
    }

    override fun stepDown() {
        confusedStep(3)
    }

    private fun confusedStep(funcNumber: Int) {
        randomHelperFunc((funcNumber + generateDelta()) % 4)
    }

    private fun generateDelta(): Int {
        return Random.nextInt(3) - 1
    }

    private fun randomHelperFunc(number: Int) {
        when (number) {
            0 -> player.stepLeft()
            1 -> player.stepUp()
            2 -> player.stepRight()
            3 -> player.stepDown()
        }
    }
}