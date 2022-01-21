package code.advent.day13.part1

import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*
import kotlin.test.assertEquals

internal class TransparentPaperFoldingTest {

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day13-paper-folding-example.txt")
        val expectedDotsAfterFirstFold = 17
        doPaperFolding(source, expectedDotsAfterFirstFold)
    }

    @Test
    internal fun actual() {
        val source = getFileSource("/day13-paper-folding-actual.txt")
        val expectedDotsAfterFirstFold = 729
        doPaperFolding(source, expectedDotsAfterFirstFold)
    }

    private fun doPaperFolding(source: File, expectedDotsAfterFirstFold: Int) {
        Scanner(source).use { scanner ->
            val parsedInputs = parseInputs(scanner)
            var dotMatrix = parsedInputs.first
            val foldInstructions = parsedInputs.second
            dotMatrix.forEach { println(it) }
            var firstFold = true
            foldInstructions.forEach {
                println(it)
                dotMatrix = when (it.first) {
                    "x" -> foldX(it, dotMatrix)
                    "y" -> foldY(it, dotMatrix)
                    else -> {
                        throw RuntimeException("Unknown instruction ${it.first}")
                    }
                }
                println("")
                println("-------")
                dotMatrix.forEach { println(it) }
                if (firstFold) {
                    val count = dotMatrix.sumOf { row -> row.count { it != 0 } }
                    assertEquals(expectedDotsAfterFirstFold, count)
                }
                firstFold = false
            }

            println("-------")
            dotMatrix.forEach {
                val formatted = it.map {
                    if (it == 0)
                        "." else "#"
                }
                println (formatted)
            }

        }
    }

    private fun foldX(
        foldInstruction: Pair<String, Int>,
        dotMatrix: MutableList<MutableList<Int>>
    ): MutableList<MutableList<Int>> {
        val colNum = foldInstruction.second
        val updated = dotMatrix.map {
            for (i in colNum + 1 until dotMatrix[0].size) {
                it[colNum - (i - colNum)] = it[colNum - (i - colNum)] + it[i]
            }
            it.take(colNum).toMutableList()
        }
        return updated.toMutableList()

    }

    private fun foldY(
        foldInstruction: Pair<String, Int>,
        dotMatrix: MutableList<MutableList<Int>>
    ): MutableList<MutableList<Int>> {
        val rowNum = foldInstruction.second
        for (i in rowNum + 1 until dotMatrix.size) {
            val row = dotMatrix[i]
            val target = dotMatrix[rowNum - (i - rowNum)]
            val updated = target.mapIndexed { index, int -> int + row[index] }.toMutableList()
            dotMatrix[rowNum - (i - rowNum)] = updated
        }
        return dotMatrix.take(rowNum).toMutableList()
    }

    private fun parseInputs(scanner: Scanner): Pair<MutableList<MutableList<Int>>, MutableList<Pair<String, Int>>> {
        val foldInstructions = mutableListOf<Pair<String, Int>>()
        val dotCoords = mutableListOf<Pair<Int, Int>>()
        var maxX = 0
        var maxY = 0
        while (scanner.hasNext()) {
            val inputLine = scanner.nextLine()
            if (inputLine.isBlank()) {
                continue
            } else if (inputLine.startsWith("fold along ")) {
                foldInstructions.add(parseFoldInstruction(inputLine))
            } else {
                val x = inputLine.split(",")[0].toInt()
                val y = inputLine.split(",")[1].toInt()
                if (x > maxX) maxX = x
                if (y > maxY) maxY = y
                dotCoords.add(Pair(x, y))
            }
        }
        val lines = MutableList(maxY + 1) { MutableList(maxX + 1) { 0 } }
        dotCoords.forEach { lines[it.second][it.first] = 1 }
        return Pair(lines, foldInstructions)
    }

    private fun parseFoldInstruction(inputLine: String): Pair<String, Int> {
        val axisAndVal = inputLine.split(" ")[2].split("=")
        return Pair(axisAndVal[0], axisAndVal[1].toInt())
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
