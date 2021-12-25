package code.advent.day8.part1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.Math.abs
import java.net.URISyntaxException
import java.util.*
import java.util.stream.Collectors

internal class ClockSegmentSearchTest {

    @Test
    internal fun actual() {
        val source = getFileSource("/day8-part1-clock-actual.txt")
        val allOutputs = mutableListOf<String>()
        try {
            Scanner(source).use { scanner ->
                while(scanner.hasNext()) {
                    val line = scanner.nextLine()
                    val output = line.split("|")[1].trim().split(" ")
                    println("outputs - $output")
                    allOutputs.addAll(output)
                }

                val counts = allOutputs.groupingBy { it.length }.eachCount()

                println(counts)
                val answer = counts[2]!! + counts[3]!! + counts[4]!! + counts[7]!!

                assertEquals(512, answer)


            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day8-part1-clock-example.txt")
        val allOutputs = mutableListOf<String>()
        try {
            Scanner(source).use { scanner ->
                while(scanner.hasNext()) {
                    val line = scanner.nextLine()
                    val output = line.split("|")[1].trim().split(" ")
                    println("outputs - $output")
                    allOutputs.addAll(output)
                }

                //                    val counts = output.map { outputWord -> outputWord.toCharArray().sorted().joinToString() }.groupingBy { it.length }.eachCount()
                val counts = allOutputs.groupingBy { it.length }.eachCount()

                println(counts)
                val answer = counts[2]!! + counts[3]!! + counts[4]!! + counts[7]!!

                assertEquals(26, answer)


            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

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
