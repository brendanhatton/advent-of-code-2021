package code.advent.day6.part1

class LanternFishSchool(csvListOfNearbyLanternFish: String) {
    var fish: List<LanternFish>

    init {
        fish = csvListOfNearbyLanternFish.split(",").map { i -> LanternFish(i.toInt()) }
    }

    fun oneDayPasses(): List<LanternFish> {
        fish = fish.flatMap { fish -> fish.dayPasses() }
        return fish
    }

    override fun toString(): String {
        return fish.map { f -> f.daysUntilReproduction }.joinToString(",")
    }



}

class LanternFish(var daysUntilReproduction: Int) {

    fun dayPasses(): List<LanternFish> {
        daysUntilReproduction--

        return when (daysUntilReproduction) {
            0 -> listOf(this)
            -1 -> {
                this.daysUntilReproduction = reproductionNumberOfDays
                return listOf(this, LanternFish(8))
            }
            else -> listOf(this)
        }
    }

    private val reproductionNumberOfDays = 6

}