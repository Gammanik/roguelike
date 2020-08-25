package graphics.model

import kotlin.random.Random

abstract class PlayerDecorator(val player: Character, checker: MapChecker) : Character(checker) {
    override var xCoordinate: Int
        get() = player.xCoordinate
        set(value) {}

    override var yCoordinate: Int
        get() = player.yCoordinate
        set(value) {}
}