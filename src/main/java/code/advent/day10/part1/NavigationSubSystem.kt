package code.advent.day10.part1

class NavigationSubSystemLine(navigationSubSystemLine: String) {

    val CHUNK_START = listOf('(', '[', '{', '<')
    val CHUNK_END = listOf(')', ']', '}', '>')
    val chunkList = mutableListOf<Chunk>()


    init {
        navigationSubSystemLine.toCharArray().forEach { char ->
            if (CHUNK_START.contains(char)) {
                chunkList.add(Chunk(char))
            } else if (CHUNK_END.contains(char)) {
                val last = chunkList.last { !it.complete }
                val chunkStartChar = last.start
                if (CHUNK_START.get(CHUNK_END.indexOf(char)) == chunkStartChar) {
                    last.complete = true
                } else {
                    throw CorruptedChunkException(char)
                }
            } else {
                throw IllegalArgumentException("Unknown Character $char")
            }
        }
    }

    fun calculateChatsToComplete(): List<Char> {
        chunkList.reverse()
        return chunkList.filter { !it.complete }.map { chunk: Chunk ->
            when (chunk.start) {
                '(' -> ')'
                '{' -> '}'
                '[' -> ']'
                '<' -> '>'
                else -> throw RuntimeException("Unknown char ${chunk.start}")
            }
        }

    }

}

class Chunk(val start: Char) {
    var complete = false
}

class CorruptedChunkException(val start: Char) : RuntimeException()
