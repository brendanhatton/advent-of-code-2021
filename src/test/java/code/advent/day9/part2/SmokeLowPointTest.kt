package code.advent.day9.part2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*

internal class SmokeLowPointTestPart2 {


    @Test
    internal fun actualPart2() {
        val source = getFileSource("/day9-part1-smoke-actual.txt")
        try {
            Scanner(source).use { scanner ->
                parseInputs(scanner, inputs)

                //find low points basin sizes
                val lowPointsBasins = identifyLowPointsBasins(inputs)
                lowPointsBasins.sort()
                lowPointsBasins.reverse()
                assertEquals(1600104, lowPointsBasins[0] * lowPointsBasins[1] * lowPointsBasins[2])
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    val inputs: MutableList<MutableList<Int>> = mutableListOf()

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day9-part1-smoke-example.txt")
        try {
            Scanner(source).use { scanner ->
                parseInputs(scanner, inputs)

                //find low points basin sizes
                val lowPointsBasins = identifyLowPointsBasins(inputs)
                lowPointsBasins.sort()
                lowPointsBasins.reverse()
                assertEquals(1134, lowPointsBasins[0] * lowPointsBasins[1] * lowPointsBasins[2])
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun identifyLowPointsBasins(inputs: MutableList<MutableList<Int>>): MutableList<Int> {
        val basinSizes = mutableListOf<Int>()
        inputs.forEachIndexed { rowIndex, row ->
            println("row $row")
            row.forEachIndexed { columnIndex, int ->
                var lowerThanAbove = false
                var lowerThanBelow = false
                var lowerThanLeft = false
                var lowerThanRight = false

                if (rowIndex == 0 || int < inputs.get(rowIndex - 1).get(columnIndex)) {
                    lowerThanAbove = true
//                    println("lower than above: $lowerThanAbove")
                }

                if (rowIndex == inputs.size - 1 || int < inputs.get(rowIndex + 1).get(columnIndex)) {
                    lowerThanBelow = true
//                    println("lower than below: $lowerThanBelow")
                }

                if (columnIndex == 0 || int < inputs.get(rowIndex).get(columnIndex - 1)) {
                    lowerThanLeft = true
//                    println("lower than left: $lowerThanLeft")
                }

                if (columnIndex == inputs.get(rowIndex).size - 1 || int < inputs.get(rowIndex).get(columnIndex + 1)) {
                    lowerThanRight = true
//                    println("lower than right: $lowerThanRight")
                }

                if (lowerThanAbove && lowerThanBelow && lowerThanLeft && lowerThanRight) {
                    println("identified lowpoint $int")
                    val basinSize = calculateBasinSize(rowIndex, columnIndex)
                    println("basin size $basinSize")
                    basinSizes.add(basinSize)
                }
            }
        }
        println("basin sizes $basinSizes")
        return basinSizes
    }

    private fun calculateBasinSize(rowIndex: Int, columnIndex: Int): Int {
        val possibleBasinPoints = Stack<Pair<Int, Int>>()
        possibleBasinPoints.push(Pair(rowIndex, columnIndex))
        return countPointsInBasin(possibleBasinPoints)

    }

    private fun countPointsInBasin(possibleBasinPoints: Stack<Pair<Int, Int>>): Int {
        val basin = mutableSetOf<Pair<Int, Int>>()
        while (possibleBasinPoints.size > 0) {
            val point = possibleBasinPoints.pop()
            basin.add(point)
            val rowIndex = point.first
            val columnIndex = point.second
            val int = inputs[rowIndex][columnIndex]
            println("looking for basin points neighbouring $point ($int)")

            if (rowIndex != 0) {
                val pointAbove = inputs.get(rowIndex - 1).get(columnIndex)
                if (int < pointAbove && pointAbove != 9) {
                    println("($int) lower than above ($pointAbove)")
                    possibleBasinPoints.push(Pair(rowIndex - 1, columnIndex))
                }
            }



            if (rowIndex != inputs.size -1) {
                val pointBelow = inputs.get(rowIndex + 1).get(columnIndex)
                if (int < pointBelow && pointBelow != 9) {
                    println("($int) lower than above ($pointBelow)")
                    possibleBasinPoints.push(Pair(rowIndex + 1, columnIndex))
                }
            }


            if (columnIndex != 0) {
                val pointToLeft = inputs.get(rowIndex).get(columnIndex - 1)
                if (int < pointToLeft && pointToLeft != 9) {
                    println("($int) lower than left ($pointToLeft)")
                    possibleBasinPoints.push(Pair(rowIndex, columnIndex-1))
                }
            }


            if (columnIndex != inputs.get(rowIndex).size - 1) {
                val pointToRight = inputs.get(rowIndex).get(columnIndex + 1)
                if (int < pointToRight && pointToRight != 9) {
                    println("($int) lower than right ($pointToRight)")
                    possibleBasinPoints.push(Pair(rowIndex, columnIndex+1))
                }
            }

        }
        return basin.size
    }

    private fun parseInputs(scanner: Scanner, inputs: MutableList<MutableList<Int>>) {
        while (scanner.hasNext()) {
            val inputLine = scanner.nextLine()
            val row = inputLine.map { it.toString().toInt() }.toMutableList()
            inputs.add(row)

        }
        println("input - $inputs")
    }


    private fun getFileSource(inputFilename: String): File {
        val resource = this.javaClass.getResource(inputFilename)
        try {
            return File(resource.toURI())
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

    }
}
