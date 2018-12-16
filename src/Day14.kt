import org.junit.jupiter.api.Test

class Day14 {
    private val puzzleInput = "ngcjuoqr"

    private fun String.getTriple(): Char? {
        var i = -1
        while (++i < this.length - 2) if (this[i] == this[i + 1] && this[i] == this[i + 2]) return this[i]
        return null
    }

    private fun String.getQuint(): Char? {
        var i = -1
        while (++i < this.length - 4) if (this[i] == this[i + 1] && this[i] == this[i + 2] && this[i] == this[i + 3] && this[i] == this[i + 4]) return this[i]
        return null
    }

    private fun solve(rounds: Int = 0): Int {
        var i = -1
        val keys = mutableListOf<String>()
        var hashes = mutableMapOf<Int, String>()
        while (keys.size < 64) {
            val hash = hashes.computeIfAbsent(++i) {
                var h = (puzzleInput + it).md5()
                for (n in 0 until rounds) h = h.md5()
                h
            }
            val triple = hash.getTriple()
            if (triple != null) {
                for (j in 1..1000) {
                    val hash2 = hashes.computeIfAbsent(i + j) {
                        var h = (puzzleInput + it).md5()
                        for (n in 0 until rounds) h = h.md5()
                        h
                    }
                    val quint = hash2.getQuint()
                    if (quint != null && triple == quint) {
                        keys.add(hash)
                        break
                    }
                }
            }
            if (i % 1000 == 0) {
                hashes = hashes.filter { it.key in hashes.keys.sortedDescending().take(1000) }.toMap().toMutableMap()
            }
        }
        return i
    }

    @Test
    fun part1() {
        println(solve())
    }

    @Test
    fun part2() {
        println(solve(2016))
    }
}
