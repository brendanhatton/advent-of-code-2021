package code.advent.day6.part2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LanternFishSchoolTest {

    @Test
    internal fun part2Actual() {

        var school = mutableMapOf<Int, Long>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(3,0), Pair(4,0), Pair(5,0), Pair(6,0), Pair(7,0), Pair(8,0))
        listOf(1,4,2,4,5,3,5,2,2,5,2,1,2,4,5,2,3,5,4,3,3,1,2,3,2,1,4,4,2,1,1,4,1,4,4,4,1,4,2,4,3,3,3,3,1,1,5,4,2,5,2,4,2,2,3,1,2,5,2,4,1,5,3,5,1,4,5,3,1,4,5,2,4,5,3,1,2,5,1,2,2,1,5,5,1,1,1,4,2,5,4,3,3,1,3,4,1,1,2,2,2,5,4,4,3,2,1,1,1,1,2,5,1,3,2,1,4,4,2,1,4,5,2,5,5,3,3,1,3,2,2,3,4,1,3,1,5,4,2,5,2,4,1,5,1,4,5,1,2,4,4,1,4,1,4,4,2,2,5,4,1,3,1,3,3,1,5,1,5,5,5,1,3,1,2,1,4,5,4,4,1,3,3,1,4,1,2,1,3,2,1,5,5,3,3,1,3,5,1,5,3,5,3,1,1,1,1,4,4,3,5,5,1,1,2,2,5,5,3,2,5,2,3,4,4,1,1,2,2,4,3,5,5,1,1,5,4,3,1,3,1,2,4,4,4,4,1,4,3,4,1,3,5,5,5,1,3,5,4,3,1,3,5,4,4,3,4,2,1,1,3,1,1,2,4,1,4,1,1,1,5,5,1,3,4,1,1,5,4,4,2,2,1,3,4,4,2,2,2,3)
                .forEach { school[it] = school[it]!!+1 }

        var day = 0

        repeat(256) {
            day++
            println("Day $day - ${school.values.sum()} fish and counting")
            school = oneDayPasses(school)
        }

        //day 256
        assertEquals(1589590444365, school.values.sum())

    }

    @Test
    internal fun part2Example() {

        var school = mutableMapOf<Int, Long>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(3,0), Pair(4,0), Pair(5,0), Pair(6,0), Pair(7,0), Pair(8,0))
        listOf(3,4,3,1,2).forEach { school[it] = school[it]!!+1 }

        var day = 0

        repeat(18) {
            day++
            println("Day $day - ${school.values.sum()} fish and counting")
            school = oneDayPasses(school)
        }

        //day 18
        assertEquals(26, school.values.sum())

        repeat(238) {
            day++
            println("Day $day - ${school.values.sum()} fish and counting")
            school = oneDayPasses(school)
        }

        //day 256
        assertEquals(26984457539, school.values.sum())

    }

    private fun oneDayPasses(school: MutableMap<Int, Long>): MutableMap<Int, Long> {
        val newSchool = mutableMapOf<Int, Long>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(3,0), Pair(4,0), Pair(5,0), Pair(6,0), Pair(7,0), Pair(8,0))
        newSchool[8] = school[0]!! //new fish
        newSchool[6] = school[0]!! + school[7]!! //reset fish that just reproduced
        newSchool[7] = school[8]!!
        newSchool[5] = school[6]!!
        newSchool[4] = school[5]!!
        newSchool[3] = school[4]!!
        newSchool[2] = school[3]!!
        newSchool[1] = school[2]!!
        newSchool[0] = school[1]!!

        return newSchool
    }

}