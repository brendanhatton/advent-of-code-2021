package code.advent.day17.part1

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.lang.Math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ProbeShootingTest {

    @Test
    internal fun shouldBehaveLikeExample() {
        val input = "target area: x=20..30, y=-10..-5"
        val targetRanges = parseInput(input)
        assertEquals(IntRange(20, 30), targetRanges.first)
        assertEquals(IntRange(-10, -5), targetRanges.second)

        var shotResult = fireProbeWithVelocity(7, 2, targetRanges)
        assertTrue(shotResult.inTargetZone)

        shotResult = fireProbeWithVelocity(6, 3, targetRanges)
        assertTrue(shotResult.inTargetZone)

        shotResult = fireProbeWithVelocity(9, 0, targetRanges)
        assertTrue(shotResult.inTargetZone)

        shotResult = fireProbeWithVelocity(17, -4, targetRanges)
        assertFalse(shotResult.inTargetZone)
        assertTrue(shotResult.overshotTarget)
        assertTrue(shotResult.overshotX)
        assertFalse(shotResult.overshotY)
    }

    @Test
    internal fun shouldCalculateMaxY() {
        val input = "target area: x=20..30, y=-10..-5"
        val targetRanges = parseInput(input)
        assertEquals(IntRange(20, 30), targetRanges.first)
        assertEquals(IntRange(-10, -5), targetRanges.second)

        val minXVelocityToReachTarget = calculateMinXVelocity(targetRanges)
        val (maxYVelocityToReachTargetSuccess, maxYLocation) = calculateMaxYValues(
            minXVelocityToReachTarget,
            targetRanges
        )

        assertEquals(9, maxYVelocityToReachTargetSuccess)
        assertEquals(45, maxYLocation)
    }

    @Test
    internal fun actual() {
        val input = "target area: x=209..238, y=-86..-59"
        val targetRanges = parseInput(input)

        val minXVelocityToReachTarget = calculateMinXVelocity(targetRanges)
        assertEquals(20, minXVelocityToReachTarget)
        val (maxYVelocityToReachTargetSuccess, maxYLocation) = calculateMaxYValues(
            minXVelocityToReachTarget,
            targetRanges
        )

        assertEquals(85, maxYVelocityToReachTargetSuccess)
        assertEquals(3655, maxYLocation)
    }

    @Test
    internal fun findAllPossibleVelocitiesExample() {
        val input = "target area: x=20..30, y=-10..-5"
        val targetRanges = parseInput(input)

        val minXVelocityToReachTarget = calculateMinXVelocity(targetRanges)

        val (maxYVelocityToReachTarget, maxYLocation) = calculateMaxYValues(
            minXVelocityToReachTarget,
            targetRanges
        )

        val successfulVelocities =  determineAllPossibleSuccessVelocities(
            minXVelocityToReachTarget,
            maxYVelocityToReachTarget,
            targetRanges
        )

        assertEquals(112, successfulVelocities.size)

    }
    @Test
    internal fun findAllPossibleVelocitiesActual() {
        val input = "target area: x=209..238, y=-86..-59"
        val targetRanges = parseInput(input)

        val minXVelocityToReachTarget = calculateMinXVelocity(targetRanges)

        val (maxYVelocityToReachTarget, maxYLocation) = calculateMaxYValues(
            minXVelocityToReachTarget,
            targetRanges
        )

        val successfulVelocities =  determineAllPossibleSuccessVelocities(
            minXVelocityToReachTarget,
            maxYVelocityToReachTarget,
            targetRanges
        )

        assertEquals(1447, successfulVelocities.size)

    }

    private fun determineAllPossibleSuccessVelocities(
        minXVelocityToReachTarget: Int,
        maxYVelocityToReachTarget: Int,
        targetRanges: Pair<IntRange, IntRange>
    ): MutableList<Pair<Int, Int>> {
        val maxXVelocityToReachTarget = targetRanges.first.last
        val minYVelocityToReachTarget = targetRanges.second.first

        val successfulVelocities = emptyList<Pair<Int, Int>>().toMutableList()
        for (x in minXVelocityToReachTarget..maxXVelocityToReachTarget) {
            for (y in minYVelocityToReachTarget..maxYVelocityToReachTarget) {
                val shotResult = fireProbeWithVelocity(x, y, targetRanges)
                if (shotResult.inTargetZone) {
                    successfulVelocities.add(Pair(x, y))
                }
            }
        }
        return successfulVelocities
    }

    /*
        When shot upwards, the probe makes a parabolic shape. When it passes zero (and one step will always equal y=0)
        the next step will be negative the initial velocity + 1. So the max range of possible y values is the bottom of
        the target zone. eg if Y range is -10..-5, the maximum vertical velocity is 9.
        The maximum height is then 9+8+7+6... which calculable by the formula n(n+1)/2
     */
    private fun calculateMaxYValues(
        minXVelocityToReachTarget: Int,
        targetRanges: Pair<IntRange, IntRange>
    ): Pair<Int, Long> {
        val maxYpossible = kotlin.math.abs(targetRanges.second.first)
        for (i in maxYpossible downTo 1) {
            val shotResult = fireProbeWithVelocity(minXVelocityToReachTarget, i, targetRanges)
            if (shotResult.inTargetZone) {
                val maxHeightReached = i * (i.toLong() + 1) / 2
                return Pair(i, maxHeightReached)
            }
        }
        throw RuntimeException("couldn't calculate an answer")
    }

    private fun calculateMinXVelocity(targetRanges: Pair<IntRange, IntRange>): Int {
        var minXVelocityToReachTarget = 0
        while ((0..minXVelocityToReachTarget).sum() < targetRanges.first.first()) {
            minXVelocityToReachTarget += 1
        }
        return minXVelocityToReachTarget
    }

    private fun fireProbeWithVelocity(
        initialXVelocity: Int,
        initialYVelocity: Int,
        targetRanges: Pair<IntRange, IntRange>
    ): ShotResult {
        var currentXVelocity = initialXVelocity
        var currentYVelocity = initialYVelocity
        var xLocation = 0
        var yLocation = 0L
        var maxYLocation = 0L
        while (true) {
            xLocation += currentXVelocity
            yLocation += currentYVelocity
            if (yLocation > maxYLocation) maxYLocation = yLocation
            println("x: $xLocation, y: $yLocation, maxY: $maxYLocation")
            if (inTargetZone(targetRanges, xLocation, yLocation)) {
                return ShotResult(true)
            } else {
                val overshotTargetZone = overshotTargetZone(targetRanges, xLocation, yLocation)
                if (overshotTargetZone.overshotTarget) {
                    return overshotTargetZone
                }
            }

            currentXVelocity = adjustXVelocity(currentXVelocity)
            currentYVelocity = adjustYVelocity(currentYVelocity)
        }
    }

    private fun adjustXVelocity(currentXVelocity: Int): Int {
        return if (currentXVelocity < 0) {
            currentXVelocity + 1
        } else if (currentXVelocity > 0) {
            currentXVelocity - 1
        } else {
            0
        }
    }

    private fun adjustYVelocity(currentYVelocity: Int): Int {
        return currentYVelocity - 1
    }

    private fun overshotTargetZone(
        targetRanges: Pair<IntRange, IntRange>,
        xLocation: Int,
        yLocation: Long
    ): ShotResult {
        val overshotX = xLocation > targetRanges.first.last
        val overshotY = yLocation < targetRanges.second.first
        return ShotResult(
            inTargetZone = false,
            overshotTarget = overshotX || overshotY,
            overshotX = overshotX,
            overshotY = overshotY
        )
    }

    private fun inTargetZone(
        targetRanges: Pair<IntRange, IntRange>,
        xLocation: Int,
        yLocation: Long
    ) = targetRanges.first.contains(xLocation) && targetRanges.second.contains(yLocation)

    private fun parseInput(input: String): Pair<IntRange, IntRange> {
        val inputToken = input.split(" ")
        val xRangeString = inputToken[2].substring(2, inputToken[2].length - 1)
        val yRangeString = inputToken[3].substring(2)
        val xRange = IntRange(xRangeString.split("..")[0].toInt(), xRangeString.split("..")[1].toInt())
        val yRange = IntRange(yRangeString.split("..")[0].toInt(), yRangeString.split("..")[1].toInt())
        return Pair(xRange, yRange)
    }

    class ShotResult(
        val inTargetZone: Boolean,
        val overshotTarget: Boolean = false,
        val overshotX: Boolean = false,
        val overshotY: Boolean = false
    )


}
