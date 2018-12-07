import org.junit.jupiter.api.Test
import java.io.File

class Day02 {
    private val instructions = File(this.javaClass.getResource("02.txt").path).readLines()

    @Test
    fun part1() {
        val pad = arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 9))
        var x = 1
        var y = 1
        val code = arrayListOf<Int>()
        for (b in instructions) {
            for (c in b) {
                when (c) {
                    'U' -> if (y > 0) y -= 1
                    'D' -> if (y < 2) y += 1
                    'L' -> if (x > 0) x -= 1
                    'R' -> if (x < 2) x += 1
                }
            }

            code.add(pad[y][x])
        }
        println(code.joinToString(""))
    }

    @Test
    fun part2() {
        val pad = arrayOf(
            arrayOf('0', '0', '1', '0', '0'),
            arrayOf('0', '2', '3', '4', '0'),
            arrayOf('5', '6', '7', '8', '9'),
            arrayOf('0', 'A', 'B', 'C', '0'),
            arrayOf('0', '0', 'D', '0', '0')
        )

        fun isAllowed(x: Int, y: Int, dir: Char): Boolean {
            if (y == 0 && x == 2 && dir != 'D') return false
            if (y == 1) {
                if (x == 1 && dir != 'R' && dir != 'D') return false
                if (x == 3 && dir != 'L' && dir != 'D') return false
            }
            if (y == 2) {
                if (x == 0 && dir != 'R') return false
                if (x == 4 && dir != 'L') return false
            }
            if (y == 3) {
                if (x == 1 && dir != 'R' && dir != 'U') return false
                if (x == 3 && dir != 'L' && dir != 'U') return false
            }
            if (y == 4 && x == 2 && dir != 'U') return false
            return true
        }

        var x = 0
        var y = 2
        val code = arrayListOf<Any>()
        for (b in instructions) {
            for (c in b) {
                when (c) {
                    'U' -> if (isAllowed(x, y, c)) y -= 1
                    'D' -> if (isAllowed(x, y, c)) y += 1
                    'L' -> if (isAllowed(x, y, c)) x -= 1
                    'R' -> if (isAllowed(x, y, c)) x += 1
                }
            }

            code.add(pad[y][x])
        }
        println(code.joinToString(""))
    }

}
