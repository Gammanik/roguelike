package graphics.model

interface GameUnit {
    val checker: MapChecker
    fun getPointsCoordinates() : ArrayList<Pair<Int,Int>>
}