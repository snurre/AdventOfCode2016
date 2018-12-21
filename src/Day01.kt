import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs

class Day01 {
    enum class Direction(val dx: Int, val dy: Int) {
        N(0, -1),
        E(1, 0),
        S(0, 1),
        W(-1, 0);

        val left by lazy {
            when (this) {
                Direction.N -> Direction.W
                Direction.E -> Direction.N
                Direction.S -> Direction.E
                Direction.W -> Direction.S
            }
        }
        val right by lazy {
            when (this) {
                Direction.N -> Direction.E
                Direction.E -> Direction.S
                Direction.S -> Direction.W
                Direction.W -> Direction.N
            }
        }
    }

    private val instructions = File("resources/01.txt").readText().trim().split(", ")
        .map { (it[0] == 'L') to it.substring(1).toInt() }

    @Test
    fun part1() {
        var x = 0
        var y = 0
        var dir = Direction.N
        for (i in instructions) {
            dir = if (i.first) dir.left else dir.right
            for (j in 0 until i.second) {
                x += dir.dx
                y += dir.dy
            }
        }
        println(abs(0 - x) + abs(0 - y))
    }

    @Test
    fun part2() {
        data class Point(val x: Int, val y: Int)

        val visited = mutableSetOf<Point>()
        var x = 0
        var y = 0
        var dir = Direction.N
        for (i in instructions) {
            dir = if (i.first) dir.left else dir.right
            for (j in 0 until i.second) {
                x += dir.dx
                y += dir.dy
                if (!visited.add(Point(x, y))) {
                    println(abs(0 - x) + abs(0 - y))
                    return
                }
            }
        }
    }

}
