import org.junit.jupiter.api.Test
import java.io.File

class Day08 {
    private val data = File("resources/08.txt").readLines()
    private val screen = {
        val screen = Array(6) { Array(50) { 0 } }
        for (s in data) {
            val p = s.split(' ')
            when (p[0]) {
                "rect" -> {
                    val r = p[1].split('x')
                    for (y in 0 until r[1].toInt()) {
                        for (x in 0 until r[0].toInt()) screen[y][x] = 1
                    }
                }
                "rotate" -> {
                    when (p[1]) {
                        "row" -> {
                            val y = p[2].split('=')[1].toInt()
                            val a = p[4].toInt()
                            val row = screen[y]
                            val r = Array(row.size) { 0 }
                            for (x in 0 until row.size) {
                                r[(x + a) % row.size] = row[x]
                            }
                            screen[y] = r
                        }
                        "column" -> {
                            val x = p[2].split('=')[1].toInt()
                            val a = p[4].toInt()
                            val c = Array(screen.size) { 0 }
                            for (y in 0 until screen.size) {
                                c[y] = screen[y][x]
                            }
                            for (y in 0 until screen.size) {
                                screen[(y + a) % screen.size][x] = c[y]
                            }
                        }
                    }
                }
            }
        }
        screen
    }.invoke()

    @Test
    fun part1() {
        println(screen.sumBy { y -> y.sum() })
    }

    @Test
    fun part2() {
        for (y in screen) {
            println(y.map { if (it == 1) '#' else ' ' }.joinToString(""))
        }
    }
}
