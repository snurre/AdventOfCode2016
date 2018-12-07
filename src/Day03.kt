import org.junit.jupiter.api.Test
import java.io.File

class Day03 {
    private val rx = Regex(" +([0-9]+) +([0-9]+) +([0-9]+)")
    private val triangles = File(this.javaClass.getResource("03.txt").path).readLines().map {
        val m = rx.matchEntire(it)!!
        listOf(m.groupValues[1].toInt(), m.groupValues[2].toInt(), m.groupValues[3].toInt())
    }

    @Test
    fun part1() {
        var valid = 0
        for (t in triangles) {
            val m = t.max()!!
            if (m < t.sum() - m) valid++
        }
        println(valid)
    }

    @Test
    fun part2() {
        var valid = 0
        for (x in 0 until 3) {
            for (y in 0 until triangles.size step 3) {
                val t = listOf(triangles[y][x], triangles[y + 1][x], triangles[y + 2][x])
                val m = t.max()!!
                if (m < t.sum() - m) valid++
            }
        }
        println(valid)
    }
}
