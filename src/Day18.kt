import org.junit.jupiter.api.Test
import java.io.File

class Day18 {
    private val safe = '.'
    private val trap = '^'
    private val instructions = File(this.javaClass.getResource("18.txt").path).readText().trim()

    private fun solve(rows: Int): List<String> {
        val result = mutableListOf(instructions)
        var s = instructions
//        var count = 0
        while (result.size < rows) {
            s = s.withIndex().joinToString("") { (i, c) ->
                val left = if (i > 0) s[i - 1] else safe
                val right = if (i < s.lastIndex) s[i + 1] else safe
                when {
                    left == trap && c == trap && right != trap -> trap
                    left != trap && c == trap && right == trap -> trap
                    left == trap && c != trap && right != trap -> trap
                    left != trap && c != trap && right == trap -> trap
                    else -> safe
                }.toString()
            }
            result.add(s)
        }
        return result
    }

    @Test
    fun part1() {
        println(solve(40).sumBy { r -> r.count { it == safe } })
    }

    @Test
    fun part2() {
        println(solve(400000).sumBy { r -> r.count { it == safe } })
    }
}
