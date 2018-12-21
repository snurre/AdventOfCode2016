import org.junit.jupiter.api.Test
import java.io.File

class Day23 {
    private val instructions = File("resources/23.txt").useLines {
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

        val instructions2 = instructions.toMutableList()

        while (++i < instructions2.size) {
            val q = instructions2[i]
            val x = q.second
            when (q.first) {
                "cpy" -> registers[q.third!!] = if (x.length == 1 && x in "a".."d") registers[x]!! else x.toInt()
                "inc" -> registers[x] = registers[x]!! + 1
                "dec" -> registers[x] = registers[x]!! - 1
                "jnz" -> {
                    if (x.length == 1 && x[0] in 'a'..'d') {
                        if (registers[x] != 0) {
                            i += q.third!!.toInt() - 1
                        }
                    } else if (x.toInt() != 0) {
                        if (q.third!![0] in 'a'..'d') {
                            i += registers[q.third!!]!! - 1
                        } else {
                            i += q.third!!.toInt() - 1
                        }
                    }
                }
                "tgl" -> {
                    val ti = i + registers[x]!! - 1
                    if (ti > 0 && ti < instructions2.size) {
                        val target = instructions2[ti]
                        when (target.first) {
                            "cpy" -> instructions2[ti] = Triple("jnz", target.second, target.third)
                            "inc" -> instructions2[ti] = Triple("dec", target.second, target.third)
                            "dec" -> instructions2[ti] = Triple("inc", target.second, target.third)
                            "jnz" -> instructions2[ti] = Triple("cpy", target.second, target.third)
                            "tgl" -> instructions2[ti] = Triple("inc", target.second, target.third)
                        }
                    }
                }
            }
//            println(
//                "${i+10}\t${q.first}\t${q.second}\t${(q.third ?: " ").padStart(2)}\t[${registers.values.joinToString(" ")}]"
//            )
        }
        println(registers["a"])
    }

    //    @Test
    fun part1() {
        solve(mutableMapOf("a" to 7, "b" to 0, "c" to 0, "d" to 0))
    }

    @Test
    fun part2() {
        solve(mutableMapOf("a" to 12, "b" to 0, "c" to 0, "d" to 0))
    }
}
