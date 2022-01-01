package code.advent.day11.part2

import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*
import kotlin.test.assertEquals

internal class OctopusTest {
    private var flashCounter = 0


    @Test
    internal fun actual() {
        val source = getFileSource("/day11-octopus-actual.txt")

        try {
            Scanner(source).use { scanner ->
                var octopusMatrix = parseInputs(scanner)
                val octopusCount = octopusMatrix[0].size * octopusMatrix.size
                var dayCount = 0
                var prevFlashCount = 0

                while(true) {
                    dayCount ++
                    println("step $dayCount")
                    octopusMatrix = step(octopusMatrix)
                    val currentFlashCount = flashCounter - prevFlashCount
                    println("step $currentFlashCount / $octopusCount")
                    if (octopusCount == currentFlashCount) break
                    prevFlashCount += currentFlashCount
                }
                assertEquals(273, dayCount)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day11-octopus-example.txt")

        try {
            Scanner(source).use { scanner ->
                var octopusMatrix = parseInputs(scanner)
                val octopusCount = octopusMatrix[0].size * octopusMatrix.size
                var dayCount = 0
                var prevFlashCount = 0

                while(true) {
                    dayCount ++
                    println("step $dayCount")
                    octopusMatrix = step(octopusMatrix)
                    val currentFlashCount = flashCounter - prevFlashCount
                    println("step $currentFlashCount / $octopusCount")
                    if (octopusCount == currentFlashCount) break
                    prevFlashCount += currentFlashCount
                }
                assertEquals(195, dayCount)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun step(octopusMatrix: List<MutableList<Int>>): List<MutableList<Int>> {
        var octopusMatrix = incrementAllOctopusEnergy(octopusMatrix)
        val toFlash = findOctopusesToFlash(octopusMatrix)
        doFlashes(toFlash, octopusMatrix)
        octopusMatrix = resetFlashedEnergyToZero(octopusMatrix)

        return octopusMatrix
    }

    private fun resetFlashedEnergyToZero(octopusMatrix: List<MutableList<Int>>): List<MutableList<Int>> {
        return octopusMatrix.map { line -> line.map { if (it > 9) 0 else it }.toMutableList() }
    }

    private fun doFlashes(toFlash: Stack<Pair<Int, Int>>, octopusMatrix: List<MutableList<Int>>) {
        while (!toFlash.isEmpty()) {
            val pair = toFlash.pop()
            flashCounter++

            for (rowOffset in -1 .. 1) {
                for(colOffset in -1..1) {
                    if (rowOffset == 0 && colOffset == 0) continue //don't self increment
                    val rowIndexToIncrement = pair.first + rowOffset
                    val colIndexToIncrement = pair.second + colOffset
                    try {
                        val currentVal = octopusMatrix[rowIndexToIncrement][colIndexToIncrement]
                        if (currentVal == 9) toFlash.add(Pair(rowIndexToIncrement, colIndexToIncrement))
                        octopusMatrix[rowIndexToIncrement][colIndexToIncrement] = currentVal + 1
                    } catch(a: IndexOutOfBoundsException) {
                        //nothing to do
                    }
                }
            }
        }
    }

    private fun findOctopusesToFlash(octopusMatrix: List<MutableList<Int>>): Stack<Pair<Int, Int>> {
        val toFlash = Stack<Pair<Int, Int>>()
        octopusMatrix.mapIndexed { rowIndex, list ->
            list.mapIndexed { colIndex, int ->
                if (int == 10) {
                    toFlash.add(Pair(rowIndex, colIndex))
                }
            }
        }
        return toFlash
    }

    private fun incrementAllOctopusEnergy(octopusMatrix: List<MutableList<Int>>): List<MutableList<Int>> {
        return octopusMatrix.map { line -> line.map { it + 1 }.toMutableList() }
    }

    private fun parseInputs(scanner: Scanner): List<MutableList<Int>> {
        val lines = mutableListOf<MutableList<Int>>()
        while (scanner.hasNext()) {
            val inputLine = scanner.nextLine()
            val line = inputLine.toCharArray().map { it.toString().toInt() }.toMutableList()
            lines.add(line)
        }
        return lines
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
