import org.junit.jupiter.api.Test
import java.io.File

class Day15 {
    private val discs = File(this.javaClass.getResource("15.txt").path).readLines().map { s ->
        val m = Regex("Disc #([0-9]+) has ([0-9]+) positions; at time=([0-9]+), it is at position ([0-9]+)\\.").matchEntire(s)!!.groupValues.toList().drop(1).map { it.toInt() }
        m[1] to m[3]
    }

    private fun solve(discs: List<Pair<Int, Int>>): Int {
        var t = -1
        while (true) {
            ++t
            for (i in 0 until discs.size) {
                if ((discs[i].second + i + t + 1) % discs[i].first != 0) break
                if (i == discs.size - 1) return t
            }
        }
    }

    @Test
    fun part1() {
        println(solve(discs))
    }

    @Test
    fun part2() {
        println(solve(discs + listOf(11 to 0)))
    }
}
