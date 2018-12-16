import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class Day16 {
    private val puzzleInput = "10011111011011001"

    private fun generate(a: String): String = a + 0 + a.reversed().map { if (it == '1') '0' else '1' }.joinToString("")
    private fun checksum(input: String): String {
        var s = input
        do {
            val sb = StringBuilder(s.length / 2)
            for (i in 0 until s.length - 1 step 2) sb.append(if (s[i] == s[i + 1]) '1' else '0')
            s = sb.toString()
        } while (s.length % 2 == 0)
        return s
    }

    private fun solve(input: String, length: Int): String {
        var data = input
        while (data.length <= length) data = generate(data)
        return checksum(data.substring(0, length))
    }

    @Test
    fun part1() {
        println(solve(puzzleInput, 272))
    }

    @Test
    fun part2() {
        println(solve(puzzleInput, 35651584))
    }
}
