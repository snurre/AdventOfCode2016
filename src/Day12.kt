import org.junit.jupiter.api.Test
import java.io.File

class Day12 {
    private val instructions = File(this.javaClass.getResource("12.txt").path).useLines {
        it.map { s ->
            val p = s.split(' ')
            Triple(
                p[0],
                if (p[1].length == 1 && p[1] in "a".."d") p[1] else p[1],
                if (p[0] == "cpy" || p[0] == "jnz") p[2] else null
            )
        }.toList()
    }

    private fun solve(input: Map<String, Int>) {
        val registers = input.toMutableMap()
        var i = -1

        while (++i < instructions.size) {
            val q = instructions[i]
            val x = q.second
            when (q.first) {
                "cpy" -> registers[q.third!!] = if (x.length == 1 && x in "a".."d") registers[x]!! else x.toInt()
                "inc" -> registers[x] = registers[x]!! + 1
                "dec" -> registers[x] = registers[x]!! - 1
                "jnz" -> {
                    if (x.length == 1 && x[0] in 'a'..'d') {
                        if (registers[x] != 0) i += q.third!!.toInt() - 1
                    } else if (x.toInt() != 0) i += q.third!!.toInt() - 1
                }
            }
        }
        println(registers["a"])
    }

    @Test
    fun part1() {
        solve(mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0))
    }

    @Test
    fun part2() {
        solve(mutableMapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0))
    }
}
