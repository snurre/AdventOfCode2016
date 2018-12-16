import org.junit.jupiter.api.Test
import java.util.*

class Day13 {
    private val puzzleInput = 1358

    private fun isOpen(x: Int, y: Int) =
        Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + puzzleInput) % 2 == 0

    private fun dijkstra(from: Point, to: Point): List<Point>? {
        data class Distance(val distance: Int, val previous: Point? = null)

        val visited = mutableSetOf<Point>()
        val unvisited = mutableListOf<Point>()
        val distances = mutableMapOf<Point, Distance>()
        distances[from] = Distance(0)
        var current = from
        while (true) {
            unvisited += current.neighbours
                .filter { it.y > 0 && it.x > 0 && it !in unvisited && it !in visited && isOpen(it.x, it.y) }
            for (pos in unvisited) {
                val distance = distances[current]!!.distance + 1
                if (distance < (distances[pos]?.distance ?: Int.MAX_VALUE)) distances[pos] = Distance(distance, current)
            }
            unvisited -= current
            visited += current
            if (current == to) {
                val route = mutableListOf(to)
                var distance = distances[to]!!
                while (distance.previous != null) {
                    route.add(distance.previous!!)
                    distance = distances[distance.previous!!]!!
                }
                route.reverse()
                return route.drop(1)
            }
            current = unvisited.sortedBy { distances[it]!!.distance }.firstOrNull() ?: break
        }
        return null
    }

    private fun flood(start: Point, maxSteps: Int): Int {
        data class Distance(val distance: Int, val location: Point)

        val unvisited = ArrayDeque<Distance>()
        val visited = mutableSetOf(start)
        unvisited += Distance(0, start)
        while (unvisited.isNotEmpty()) {
            val current = unvisited.poll()
            unvisited += current.location.neighbours
                .filter { it.x >= 0 && it.y >= 0 && it !in visited && isOpen(it.x, it.y) }
                .map { Distance(current.distance + 1, it) }
                .filter { it.distance <= maxSteps }
            visited += current.location
        }
        return visited.size
    }

    @Test
    fun part1() {
        println(dijkstra(Point(1, 1), Point(31, 39))?.size)
    }

    @Test
    fun part2() {
        println(flood(Point(1, 1), 50))
    }
}
