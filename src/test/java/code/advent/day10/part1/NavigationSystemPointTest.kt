package code.advent.day10.part1

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
        try {
            Scanner(source).use { scanner ->
                val corruptedChunkChars = parseInputs(scanner)

                syntaxErrorScore = corruptedChunkChars.sumOf { char ->
                    when (char) {
                        ')' -> 3 as Int
                        ']' -> 57 as Int
                        '}' -> 1197 as Int
                        '>' -> 25137 as Int
                        else -> throw RuntimeException("Unknown character $char")
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        assertEquals(299793, syntaxErrorScore)
    }

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day10-part1-navigation-example.txt")
        val syntaxErrorScore: Int
        try {
            Scanner(source).use { scanner ->
                val corruptedChunkChars = parseInputs(scanner)

                syntaxErrorScore = corruptedChunkChars.sumOf { char ->
                    when (char) {
                        ')' -> 3 as Int
                        ']' -> 57 as Int
                        '}' -> 1197 as Int
                        '>' -> 25137 as Int
                        else -> throw RuntimeException("Unknown character $char")
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        assertEquals(26397, syntaxErrorScore)
    }

    private fun parseInputs(scanner: Scanner): MutableList<Char> {
        val corruptedChunkChars = mutableListOf<Char>()
        while (scanner.hasNext()) {
            val inputLine = scanner.nextLine()
            try {
                NavigationSubSystemLine(inputLine)
            } catch (cce: CorruptedChunkException) {
                println("corrupted chunk - char ${cce.start}")
                corruptedChunkChars.add(cce.start)
            }
        }
        return corruptedChunkChars
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
