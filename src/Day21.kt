import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class Day21 {
    private val instructions = File("resources/21.txt").readLines()
    private val rotations = mapOf(
        0 to mapOf(false to ("right" to 1), true to ("left" to 1)),
        1 to mapOf(false to ("right" to 2), true to ("left" to 1)),
        2 to mapOf(false to ("right" to 3), true to ("right" to 2)),
        3 to mapOf(false to ("right" to 4), true to ("left" to 2)),
        4 to mapOf(false to ("left" to 2), true to ("right" to 1)),
        5 to mapOf(false to ("left" to 1), true to ("left" to 3)),
        6 to mapOf(false to ("right" to 0), true to ("left" to 0)),
        7 to mapOf(false to ("right" to 1), true to ("right" to 4))
    )

    enum class X(val rx: Regex) {
        SwapPosition(Regex("swap position ([0-9]+) with position ([0-9]+)")),
        SwapLetter(Regex("swap letter ([a-z]) with letter ([a-z])")),
        RotateSteps(Regex("rotate (left|right) ([0-9]+) steps?")),
        RotatePosition(Regex("rotate based on position of letter ([a-z]+)")),
        Reverse(Regex("reverse positions ([0-9]+) through ([0-9]+)")),
        Move(Regex("move position ([0-9]+) to position ([0-9]+)")),
    }

    private fun scramble(input: String, reversed: Boolean = false): String {
        var s = input
        for (i in if (reversed) instructions.reversed() else instructions) {
            s = when {
                X.SwapPosition.rx.matches(i) -> {
                    val m = X.SwapPosition.rx.matchEntire(i)!!.groupValues.drop(1)
                    val a = m[0].toInt()
                    val b = m[1].toInt()
                    s.replaceRange(a..a, s.subSequence(b..b)).replaceRange(b..b, s.subSequence(a..a))
                }
                X.SwapLetter.rx.matches(i) -> {
                    val m = X.SwapLetter.rx.matchEntire(i)!!.groupValues.drop(1)
                    val a = s.indexOf(m[0][0])
                    val b = s.indexOf(m[1][0])
                    s.replaceRange(a..a, s.subSequence(b..b)).replaceRange(b..b, s.subSequence(a..a))
                }
                X.RotateSteps.rx.matches(i) -> {
                    val m = X.RotateSteps.rx.matchEntire(i)!!.groupValues.drop(1)
                    val direction = m[0]
                    val steps = m[1].toInt()
                    val a = ArrayDeque<Char>(s.toList())
                    for (j in 0 until steps) {
                        if (direction == "right") {
                            if (reversed) a.addLast(a.pollFirst()) else a.addFirst(a.pollLast())
                        } else {
                            if (reversed) a.addFirst(a.pollLast()) else a.addLast(a.pollFirst())
                        }
                    }
                    a.joinToString("")
                }
                X.RotatePosition.rx.matches(i) -> {
                    val m = X.RotatePosition.rx.matchEntire(i)!!.groupValues.drop(1)
                    val x = rotations[s.indexOf(m[0])]!![reversed]!!
                    val a = ArrayDeque<Char>(s.toList())
                    for (j in 0 until x.second) if (x.first == "right") a.addFirst(a.pollLast()) else a.addLast(a.pollFirst())
                    a.joinToString("")
                }
                X.Reverse.rx.matches(i) -> {
                    val m = X.Reverse.rx.matchEntire(i)!!.groupValues.drop(1)
                    val a = m[0].toInt()
                    val b = m[1].toInt()
                    s.replaceRange(a..b, s.subSequence(a..b).reversed())
                }
                X.Move.rx.matches(i) -> {
                    val m = X.Move.rx.matchEntire(i)!!.groupValues.drop(1)
                    val a = m[if (reversed) 1 else 0].toInt()
                    val b = m[if (reversed) 0 else 1].toInt()
                    val sb = StringBuilder()
                    sb.append(s)
                    val c = s[a]
                    sb.deleteCharAt(a)
                    sb.insert(b, c)
                    sb.toString()
                }
                else -> throw RuntimeException(i)
            }
        }
        return s
    }

    @Test
    fun part1() {
        println(scramble("abcdefgh"))
    }

    @Test
    fun part2() {
        println(scramble("fbgdceah", true))
    }
}
