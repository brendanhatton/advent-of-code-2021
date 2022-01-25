package code.advent.day16.part1

import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigInteger
import java.net.URISyntaxException
import java.util.*
import kotlin.test.assertEquals

internal class BitsConversionTest {

    @Test
    internal fun shouldBehaveLikeExample() {
        val source = "D2FE28"
        val binaryString = convertHexToBinary(source)
        assertEquals("110100101111111000101000", binaryString)
        val packet = Packet(binaryString)
        assertEquals(2021, packet.value)
    }

    @Test
    internal fun shouldBehaveLikeExample2() {
        val source = "38006F45291200"
        val binaryString = convertHexToBinary(source)
        assertEquals("00111000000000000110111101000101001010010001001000000000", binaryString)
        val packet = Packet(binaryString)
        assertEquals(10, packet.packets[0].value)
        assertEquals(20, packet.packets[1].value)
        assertEquals(2, packet.packets.size)
    }

    @Test
    internal fun shouldBehaveLikeExample3() {
        val source = "EE00D40C823060"
        val binaryString = convertHexToBinary(source)
        assertEquals("11101110000000001101010000001100100000100011000001100000", binaryString)
        val packet = Packet(binaryString)
        assertEquals(1, packet.packets[0].value)
        assertEquals(2, packet.packets[1].value)
        assertEquals(3, packet.packets[2].value)
        assertEquals(3, packet.packets.size)
    }

    @Test
    internal fun shouldBehaveLikeExample4() {
        val source = "8A004A801A8002F478"
        val binaryString = convertHexToBinary(source)
        val packet = Packet(binaryString)
        assertEquals(16, packet.getVersionSum())
    }

    @Test
    internal fun shouldBehaveLikeExample5() {
        val source = "620080001611562C8802118E34"
        val binaryString = convertHexToBinary(source)
        val packet = Packet(binaryString)
        assertEquals(12, packet.getVersionSum())
    }

    @Test
    internal fun shouldBehaveLikeExample6() {
        val source = "C0015000016115A2E0802F182340"
        val binaryString = convertHexToBinary(source)
        val packet = Packet(binaryString)
        assertEquals(23, packet.getVersionSum())
    }

    @Test
    internal fun shouldBehaveLikeExample7() {
        val source = "A0016C880162017C3686B18A3D4780"
        val binaryString = convertHexToBinary(source)
        val packet = Packet(binaryString)
        assertEquals(31, packet.getVersionSum())
    }

    @Test
    internal fun sneakyPrefixConversion() {
        val source = "0055"
        val binaryString = convertHexToBinary(source)
        assertEquals("0000000001010101", binaryString)
    }

    @Test
    internal fun actual() {
        val source =
            "005532447836402684AC7AB3801A800021F0961146B1007A1147C89440294D005C12D2A7BC992D3F4E50C72CDF29EECFD0ACD5CC016962099194002CE31C5D3005F401296CAF4B656A46B2DE5588015C913D8653A3A001B9C3C93D7AC672F4FF78C136532E6E0007FCDFA975A3004B002E69EC4FD2D32CDF3FFDDAF01C91FCA7B41700263818025A00B48DEF3DFB89D26C3281A200F4C5AF57582527BC1890042DE00B4B324DBA4FAFCE473EF7CC0802B59DA28580212B3BD99A78C8004EC300761DC128EE40086C4F8E50F0C01882D0FE29900A01C01C2C96F38FCBB3E18C96F38FCBB3E1BCC57E2AA0154EDEC45096712A64A2520C6401A9E80213D98562653D98562612A06C0143CB03C529B5D9FD87CBA64F88CA439EC5BB299718023800D3CE7A935F9EA884F5EFAE9E10079125AF39E80212330F93EC7DAD7A9D5C4002A24A806A0062019B6600730173640575A0147C60070011FCA005000F7080385800CBEE006800A30C023520077A401840004BAC00D7A001FB31AAD10CC016923DA00686769E019DA780D0022394854167C2A56FB75200D33801F696D5B922F98B68B64E02460054CAE900949401BB80021D0562344E00042A16C6B8253000600B78020200E44386B068401E8391661C4E14B804D3B6B27CFE98E73BCF55B65762C402768803F09620419100661EC2A8CE0008741A83917CC024970D9E718DD341640259D80200008444D8F713C401D88310E2EC9F20F3330E059009118019A8803F12A0FC6E1006E3744183D27312200D4AC01693F5A131C93F5A131C970D6008867379CD3221289B13D402492EE377917CACEDB3695AD61C939C7C10082597E3740E857396499EA31980293F4FD206B40123CEE27CFB64D5E57B9ACC7F993D9495444001C998E66B50896B0B90050D34DF3295289128E73070E00A4E7A389224323005E801049351952694C000"
        val binaryString = convertHexToBinary(source)
        println("binary string length = ${binaryString.length}")
        val packet = Packet(binaryString)
        assertEquals(981, packet.getVersionSum())
    }

    private fun convertHexToBinary(source: String): String {
        var trimmedSource = source
        var paddingCount = 0
        while (trimmedSource.startsWith("0")) {
            paddingCount++
            trimmedSource = trimmedSource.substring(1)
        }

        val binaryString = BigInteger(source, 16).toString(2)
        val prefix = when (trimmedSource.first().toString()) {
            "1" -> "000"
            "2", "3" -> "00"
            "4", "5", "6", "7" -> "0"
            else -> ""
        }
        val paddingPrefex = "0000".repeat(paddingCount)
        return paddingPrefex + prefix + binaryString
    }

}
