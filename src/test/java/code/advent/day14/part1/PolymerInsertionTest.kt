package code.advent.day14.part1

import org.junit.jupiter.api.Test
import java.io.File
import java.net.URISyntaxException
import java.util.*
import kotlin.collections.HashMap

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
            val tokenCountMap = HashMap<String, Long>()
            polymer.mapIndexed { index, c ->
                if (index < polymer.length - 1) {
                    val polymerPair = "$c${polymer[index + 1]}"
                    incrementCount(tokenCountMap, polymerPair)
                }
            }

            for (i in 1..40) {
                println(i)
                val newTokens = emptyList<Pair<String, Long>>().toMutableList()
                val destroyedTokens = emptyList<Pair<String, Long>>().toMutableList()
                tokenCountMap.forEach { (token, count) ->
                    if (count > 0 ) {
                        val toInsert = rules[token]
                        toInsert?.apply {
                            val newToken1 = token.first() + this
                            val newToken2 = this + token.last()
                            newTokens.add(Pair(newToken1, count))
                            newTokens.add(Pair(newToken2, count))
                            destroyedTokens.add(Pair(token, count))
                        }
                    }
                }
                println(newTokens)
                newTokens.forEach { incrementCount(tokenCountMap, it.first, it.second) }
                destroyedTokens.forEach { decrementCount(tokenCountMap, it.first, it.second) }
                println(tokenCountMap)
            }

            println(tokenCountMap)

            val letterCountMap = HashMap<String, Long>()
            tokenCountMap.forEach { (t, count) ->
                incrementCount(letterCountMap, t.first().toString(), count)
                incrementCount(letterCountMap, t.last().toString(), count)
            }

            //start and end
            letterCountMap[polymer.first().toString()] = letterCountMap[polymer.first().toString()]!! + 1
            letterCountMap[polymer.last().toString()] = letterCountMap[polymer.last().toString()]!! + 1

            println(letterCountMap.map { (k, v) -> "$k=${v/2}"  })
        }
    }

    private fun incrementCount(
        map: HashMap<String, Long>,
        key: String,
        amount: Long = 1
    ) {
        if (map[key] == null) {
            map[key] = amount
        } else {
            map[key] = map[key]!! + amount
        }
    }
    private fun decrementCount(
        map: HashMap<String, Long>,
        key: String,
        amount: Long = 1
    ) {
        if (map[key] == null) {
            throw RuntimeException("Cant decrement key that doesn't exist - $key")
        } else {
            map[key] = map[key]!! - amount
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
