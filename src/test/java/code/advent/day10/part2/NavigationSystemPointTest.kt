package code.advent.day10.part2

import code.advent.day10.part1.CorruptedChunkException
import code.advent.day10.part1.NavigationSubSystemLine
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*
import kotlin.test.assertEquals

internal class NavigationSystemPointTest {



    @Test
    internal fun actual() {
        val source = getFileSource("/day10-part1-navigation-actual.txt")
        val syntaxErrorScore: Int
        val winner: Long
        try {
            Scanner(source).use { scanner ->
                val parsedInputs = parseInputs(scanner)
                val lines = parsedInputs.first
                val corruptedChunkChars = parsedInputs.second

                syntaxErrorScore = corruptedChunkChars.sumOf { char ->
                    when (char) {
                        ')' -> 3 as Int
                        ']' -> 57 as Int
                        '}' -> 1197 as Int
                        '>' -> 25137 as Int
                        else -> throw RuntimeException("Unknown character $char")
                    }
                }

                val incompleteLines = lines.filter { it.chunkList.any { list -> !list.complete } }
                val charsToComplete = incompleteLines.map { line -> line.calculateChatsToComplete() }
                println(charsToComplete)
                val scoreForLine = charsToComplete.map { charsNeededToCompleteLine ->
                    var lineScore = 0L
                    charsNeededToCompleteLine.map { c ->
                        lineScore = lineScore * 5 + when (c) {
                            ')' -> 1
                            ']' -> 2
                            '}' -> 3
                            '>' -> 4
                            else -> throw RuntimeException("Unsupported Character $c")
                        }
                    }
                    lineScore
                }
                println(scoreForLine)

                val sorted = scoreForLine.sorted()
                winner = sorted[(sorted.size / 2)]
                println("winner $winner")
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        assertEquals(299793, syntaxErrorScore)
        assertEquals(3654963618, winner)
    }

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day10-part1-navigation-example.txt")
        val syntaxErrorScore: Int
        val winner: Int
        try {
            Scanner(source).use { scanner ->
                val parsedInputs = parseInputs(scanner)
                val lines = parsedInputs.first
                val corruptedChunkChars = parsedInputs.second

                syntaxErrorScore = corruptedChunkChars.sumOf { char ->
                    when (char) {
                        ')' -> 3 as Int
                        ']' -> 57 as Int
                        '}' -> 1197 as Int
                        '>' -> 25137 as Int
                        else -> throw RuntimeException("Unknown character $char")
                    }
                }

                val incompleteLines = lines.filter { it.chunkList.any { list -> !list.complete } }
                val charsToComplete = incompleteLines.map { line -> line.calculateChatsToComplete() }
                println(charsToComplete)
                val scoreForLine = charsToComplete.map { charsNeededToCompleteLine ->
                    var lineScore = 0 as Int
                    charsNeededToCompleteLine.map { c ->
                        lineScore = lineScore * 5 + when (c) {
                            ')' -> 1
                            ']' -> 2
                            '}' -> 3
                            '>' -> 4
                            else -> throw RuntimeException("Unsupported Character $c")
                        }
                    }
                    lineScore
                }
                println(scoreForLine)

                val sorted = scoreForLine.sorted()
                winner = sorted[(sorted.size / 2)]
                println("winner $winner")
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        assertEquals(26397, syntaxErrorScore)
        assertEquals(288957, winner)
    }

    private fun parseInputs(scanner: Scanner): Pair<MutableList<NavigationSubSystemLine>, MutableList<Char>> {
        val corruptedChunkChars = mutableListOf<Char>()
        val lines = mutableListOf<NavigationSubSystemLine>()
        while (scanner.hasNext()) {
            val inputLine = scanner.nextLine()
            try {
                val line = NavigationSubSystemLine(inputLine)
                lines.add(line)
            } catch (cce: CorruptedChunkException) {
                println("corrupted chunk - char ${cce.start}")
                corruptedChunkChars.add(cce.start)
            }
        }
        return Pair(lines, corruptedChunkChars)
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
