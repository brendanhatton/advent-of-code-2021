package code.advent.day6.part1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*

internal class SmokeLowPointTest {


    @Test
    internal fun actual() {
        val source = getFileSource("/day9-part1-smoke-actual.txt")
        val inputs: MutableList<MutableList<Int>> = mutableListOf()
        try {
            Scanner(source).use { scanner ->
                parseInputs(scanner, inputs)

                //find low points
                val lowPoints = identifyLowPoints(inputs)
                val totalRiskScore = lowPoints.map { it + 1 }.sum()
                assertEquals(512, totalRiskScore)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day9-part1-smoke-example.txt")
        val inputs: MutableList<MutableList<Int>> = mutableListOf()
        try {
            Scanner(source).use { scanner ->
                parseInputs(scanner, inputs)

                //find low points
                val lowPoints = identifyLowPoints(inputs)
                val totalRiskScore = lowPoints.map { it + 1 }.sum()
                assertEquals(15, totalRiskScore)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun identifyLowPoints(inputs: MutableList<MutableList<Int>>): MutableList<Int> {
        val lowPoints = mutableListOf<Int>()
        inputs.forEachIndexed { rowIndex, row ->
            println("row $row")
            row.forEachIndexed { columnIndex, int ->
                println("int $int")
                var lowerThanAbove = false
                var lowerThanBelow = false
                var lowerThanLeft = false
                var lowerThanRight = false

                if (rowIndex == 0 || int < inputs.get(rowIndex - 1).get(columnIndex)) {
                    lowerThanAbove = true
                    println("lower than above: $lowerThanAbove")
                }


                if (rowIndex == inputs.size - 1 || int < inputs.get(rowIndex + 1).get(columnIndex)) {
                    lowerThanBelow = true
                    println("lower than below: $lowerThanBelow")
                }


                if (columnIndex == 0 || int < inputs.get(rowIndex).get(columnIndex - 1)) {
                    lowerThanLeft = true
                    println("lower than left: $lowerThanLeft")
                }


                if (columnIndex == inputs.get(rowIndex).size - 1 || int < inputs.get(rowIndex).get(columnIndex + 1)) {
                    lowerThanRight = true
                    println("lower than right: $lowerThanRight")
                }




                if (lowerThanAbove && lowerThanBelow && lowerThanLeft && lowerThanRight) {
                    println("adding lowpoint $int")
                    lowPoints.add(int)
                }
            }
        }
        println("low points $lowPoints")
        return lowPoints
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