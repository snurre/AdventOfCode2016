import org.junit.jupiter.api.Test

class Day17 {
    private val puzzleInput = "lpvhkcbi"

    enum class Direction(private val offset: Int, private val check: (Int) -> Boolean) {
        UP(-4, { it > 3 }),
        DOWN(4, { it < 12 }),
        LEFT(-1, { (it % 4) > 0 }),
        RIGHT(1, { (it % 4) < 3 });

        fun isPossible(position: Int) = check.invoke(position)
        fun move(position: Int) = position + offset
        fun toChar() = name[0]
    }

    private fun solve(passcode: String, position: Int = 0, pathsTaken: List<Set<Char>> = emptyList()): List<String> =
        if (position == 15) {
            listOf(pathsTaken.map { it.first() }.joinToString(""))
        } else {
            passcode.possibleDirections(pathsTaken.map { it.first() }.joinToString("")).filter { it.isPossible(position) }.flatMap { solve(passcode, it.move(position), pathsTaken.plus<Set<Char>>(setOf(it.toChar()))) }
        }

    private fun String.possibleDirections(path: String = "") = (this + path).md5().toCharArray().take(4).map { it > 'a' }.zip(Direction.values()).associate { it.second to it.first }.filterValues { it }.keys

    @Test
    fun part1() {
        println(solve(puzzleInput).minBy { it.length })
    }

    @Test
    fun part2() {
        println(solve(puzzleInput).map { it.length }.max())
    }
}
