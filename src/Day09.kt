import org.junit.jupiter.api.Test
import java.io.File

class Day09 {
    private val markerRx = Regex("\\(([0-9]+)x([0-9]+)\\)")
    private val data = File("resources/09.txt").readText().trim()

    private fun getLength(s: CharSequence, v: Int): Long {
        var sum = 0L
        var i = -1
        while (++i < s.length) {
            if (s[i] == '(') {
                val marker = markerRx.matchEntire(s.substring(i, s.indexOf(')', i) + 1))!!
                val length = marker.groupValues[1].toInt()
                val sequence = s.subSequence(marker.value.length + i, marker.value.length + length + i)
                sum += marker.groupValues[2].toInt() * (if (v == 2 && markerRx.containsMatchIn(sequence)) getLength(sequence, v) else length.toLong())
                i += marker.value.length + length -1
            }
        }
        return sum
    }

    @Test
    fun part1() {
        println(getLength(data, 1))
    }

    @Test
    fun part2() {
        println(getLength(data, 2))
    }

}
