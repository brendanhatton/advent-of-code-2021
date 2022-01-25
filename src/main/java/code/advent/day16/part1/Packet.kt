package code.advent.day16.part1


class Packet(source: String) {

    val version: Int
    val typeId: Int
    var value: Long? = null
    var remainingBits: String = ""
    val packets: MutableList<Packet> = emptyList<Packet>().toMutableList()



    init {
        version = source.take(3).toInt(2)
        typeId = source.substring(3, 6).toInt(2)
        val payloadWithoutHeader = source.substring(6)

        when (typeId) {
            4 -> {
                val result = extractLiteral(payloadWithoutHeader)
                value = result.first
                remainingBits = result.second
            }
            else -> handleOperator(payloadWithoutHeader)
        }
    }

    fun getVersionSum(): Int {
        return version + packets.sumOf { it.getVersionSum() }
    }

    private fun extractLiteral(source: String): Pair<Long, String> {
        var payload = source
        var lastNum = false
        var result = ""
        while (!lastNum) {
            val bits = payload.take(5)
            if (bits.startsWith("0"))
                lastNum = true
            result += bits.substring(1)
            payload = payload.substring(5)
        }

        return Pair(result.toLong(2), payload)
    }

    private fun handleOperator(source: String) {
        val lengthTypeId = source.first().toString()
        when(lengthTypeId) {
            "0" -> parseSubpacketByBitLength(source)
            "1" -> parseSubpacketByNumberOfPackets(source)
            else -> throw RuntimeException("Unexpected operator length type id $lengthTypeId")
        }
    }

    private fun parseSubpacketByBitLength(source: String) {
        val totalLengthOfSubpackets = source.substring(1, 16).toInt(2)
        var bitsToBeProcessed = source.substring(16, (16 + totalLengthOfSubpackets))
        remainingBits = source.substring(16 + totalLengthOfSubpackets)
        while (bitsToBeProcessed.isNotEmpty()) {
            val subPacket = Packet(bitsToBeProcessed)
            packets.add(subPacket)
            bitsToBeProcessed = subPacket.remainingBits
        }
    }

    private fun parseSubpacketByNumberOfPackets(source: String) {
        val totalNumberOfSubpackets = source.substring(1, 12).toInt(2)
        var bitsToBeProcessed = source.substring(12)
        while (packets.size < totalNumberOfSubpackets) {
            val subPacket = Packet(bitsToBeProcessed)
            packets.add(subPacket)
            bitsToBeProcessed = subPacket.remainingBits
        }
        remainingBits = bitsToBeProcessed
    }

}
