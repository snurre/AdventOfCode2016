import org.junit.jupiter.api.Test
import java.util.*

class Day19 {
    private val puzzleInput = 3001330

    @Test
    fun part1() {
        val queue = ArrayDeque<Int>((1..puzzleInput).toList())
        while (queue.size > 1) {
            queue.add(queue.pop())
            queue.pop()
        }
        println(queue.first())
    }

    @Test
    fun part2() {
        val left = ArrayDeque<Int>((1..puzzleInput / 2).toList())
        val right = ArrayDeque<Int>(((puzzleInput / 2) + 1..puzzleInput).toList())
        while (left.isNotEmpty() && right.isNotEmpty()) {
            if (left.size > right.size) left.pollLast()
            else right.pollFirst()
            right.addLast(left.pollFirst())
            left.addLast(right.pollFirst())
        }
        println(left.firstOrNull() ?: right.first())
    }
}
