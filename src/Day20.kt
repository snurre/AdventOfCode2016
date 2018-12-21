import org.junit.jupiter.api.Test
import java.io.File

class Day20 {
    private val blacklistedIpRanges = File("resources/20.txt").readLines().map { s -> s.split("-").map { it.toLong() }.let { it[0]..it[1] } }

    private fun findAllowedRanges(): List<LongRange> {
        val allowedRanges = mutableListOf<LongRange>()
        var remainingRanges = blacklistedIpRanges
        var ip = -1L
        while (++ip <= UInt.MAX_VALUE.toLong()) {
            val matchingRanges = blacklistedIpRanges.filter { ip in it }
            if (matchingRanges.isEmpty()) {
                remainingRanges = remainingRanges.filter { it.start > ip }
                val allowedRange = ip until remainingRanges.sortedBy { it.start }.first().start
                allowedRanges.add(allowedRange)
                ip = allowedRange.endInclusive
            } else {
                ip = matchingRanges.maxBy { it.endInclusive }!!.endInclusive
            }
        }
        return allowedRanges
    }

    @Test
    fun part1() {
        println(findAllowedRanges().map { it.start }.min())
    }

    @Test
    fun part2() {
        println(findAllowedRanges().sumBy { it.count() })
    }
}
