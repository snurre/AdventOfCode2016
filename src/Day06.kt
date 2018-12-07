import org.junit.jupiter.api.Test
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

class Day06 {
    private val charFrequency = {
        val data = File(this.javaClass.getResource("06.txt").path).readLines()
        val chars = Array(data[0].length) { mutableMapOf<Char, AtomicInteger>() }
        for (s in data) {
            for (i in 0 until s.length) {
                chars[i].computeIfAbsent(s[i]) { AtomicInteger() }.incrementAndGet()
            }
        }
        chars
    }.invoke()

    @Test
    fun part1() {
        println(charFrequency.map { i -> i.maxBy { c -> c.value.toInt() }!!.key }.joinToString(""))
    }

    @Test
    fun part2() {
        println(charFrequency.map { i -> i.minBy { c -> c.value.toInt() }!!.key }.joinToString(""))
    }

}
