package code.advent.day14.part1

import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*
import javax.naming.ldap.PagedResultsControl
import kotlin.collections.HashMap
import kotlin.test.assertEquals

internal class PolymerInsertionTest {

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = getFileSource("/day14-polymer-insertion-example.txt")
        val expectedResults = listOf(
            "NNCB",
            "NCNBCHB",
            "NBCCNBBBCBHCB",
            "NBBBCNCCNBBNBNBBCHBHHBCHB",
            "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"
        )
        doWork(source, expectedResults)
    }

    @Test
    internal fun actual() {
        val source = getFileSource("/day14-polymer-insertion-actual.txt")
        doWork(source, emptyList())
    }

    private fun doWork(source: File, expectedResults: List<String>) {
        Scanner(source).use { scanner ->
            val parsedInputs = parseInputs(scanner)
            val polymer = parsedInputs.first
            val rules = parsedInputs.second
//            assertEquals(expectedResults[0], polymer)
            var updated = polymer
            for (i in 1..10) {
                updated = updated.mapIndexed { index, c ->
                    if (index < updated.length - 1) {
                        val polymerPair = "$c${updated[index + 1]}"
                        val toInsert = rules[polymerPair]
                        "$c${toInsert?.let { toInsert }}"
                    } else {
                        "$c"
                    }

                }.joinToString("")
                println(updated)
//                assertEquals(expectedResults[i], updated)
            }

            val count = HashMap<Char, Int>()
            updated.forEach { c ->
                count[c] = if (count[c] == null) {
                    1
                } else {
                    count[c]!! + 1
                }
            }

            println(count)
//            assertEquals(count['B'], 1749)
//            assertEquals(count['C'], 298)


        }
    }


    private fun parseInputs(scanner: Scanner): Pair<String, HashMap<String, String>> {
        val polymerString = scanner.nextLine()
        scanner.nextLine() //delimeter

        val ruleMap = HashMap<String, String>()
        while (scanner.hasNext()) {
            val rule = scanner.nextLine()
            val ruleTokens = rule.split(" -> ")
            ruleMap[ruleTokens[0]] = ruleTokens[1]
        }
        return Pair(polymerString, ruleMap)
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
