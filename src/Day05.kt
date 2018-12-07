import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.MessageDigest

class Day05 {
    private val puzzleInput = "reyedfim"
    private val prefix = "00000"
    private val md: MessageDigest = MessageDigest.getInstance("MD5")

    private fun String.md5(): String {
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    //    @Test
    fun part1() {
        var pw = ""
        var i = 0
        while (true) {
            val hash = (puzzleInput + i++).md5()
            if (hash.startsWith(prefix)) {
                pw += hash[5]
                if (pw.length == 8) {
                    println(pw)
                    return
                }
            }
        }
    }

    @Test
    fun part2() {
        val pw = Array(8) { '?' }
        var i = 1617992
        while (true) {
            val hash = (puzzleInput + i++).md5()
            if (hash.startsWith(prefix)) {
                if (hash[5] !in '0'..'7') continue
                val p = hash[5].toInt() - 48
                if (pw[p] != '?') continue
                pw[p] = hash[6]
                if ('?' !in pw) {
                    println(pw.joinToString(""))
                    return
                }
            }
        }
    }
}
