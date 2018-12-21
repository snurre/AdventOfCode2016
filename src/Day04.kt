import org.junit.jupiter.api.Test
import java.io.File

class Day04 {
    private val rx = Regex("([a-z-]+)-([0-9]+)\\[([a-z]+)\\]")
    private val ids = File("resources/04.txt").readLines().map {
        val m = rx.matchEntire(it)
        if (m == null) {
            println(it)
        }
        Triple(m!!.groupValues[1], m.groupValues[2].toInt(), m.groupValues[3])
    }
    private val realRooms = {
        val rooms = arrayListOf<Triple<String, Int, String>>()
        for (id in ids) {
            val chars = ('a'..'z').map { it to 0 }.toMap().toMutableMap()
            for (c in id.first) if (c != '-') chars[c] = chars[c]!! + 1
            val checksum =
                chars.entries.sortedWith(compareBy({ -it.value }, { it.key })).map { it.key }.take(5).joinToString("")
            if (checksum == id.third) rooms.add(id)
        }
        rooms
    }.invoke()

    @Test
    fun part1() {
        println(realRooms.map { it.second }.sum())
    }

    @Test
    fun part2() {
        for (room in realRooms) {
            if ("northpole" in caesar(room.first, room.second % 26)) {
                println("${room.second}")
                break
            }
        }
    }

    private fun caesar(input: String, shift: Int): String {
        val output = StringBuilder()
        for (c in input) {
            if (c !in 'a'..'z') {
                output.append(c)
                continue
            }
            output.append(
                if ((c.toInt() + shift).toChar() > 'z')
                    (c.toInt() - (26 - shift)).toChar()
                else
                    (c.toInt() + shift).toChar()
            )
        }
        return output.toString()
    }
}
