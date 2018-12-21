import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs

class Day22 {

    data class Node(val location: Point, val size: Int, val used: Int, val avail: Int)

    private val nodes: List<Node>

    init {
        val rx = Regex("/dev/grid/node-x([0-9]+)-y([0-9]+) +([0-9]+)T +([0-9]+)T +([0-9]+)T +([0-9]+)%")
        nodes = File("resources/22.txt").readLines().drop(2)
            .map { s -> rx.matchEntire(s)!!.groupValues.drop(1).map { it.toInt() } }
            .map { Node(Point(it[0], it[1]), it[2], it[3], it[4]) }
    }

    @Test
    fun part1() {
        println(nodes.filter { it.used > 0 }.sumBy { a -> nodes.count { b -> a != b && b.avail >= a.used } })
    }

    @Test
    fun part2() {
        val size = nodes.maxBy { it.location.x }!!.location.x
        val start = nodes.sortedBy { it.location }.first { it.size > 250 }.location.left
        println(nodes.first { it.used == 0 }.location.manhattan(start) + abs(start.x - size) + start.y + (5 * (size - 1)))
    }
}
