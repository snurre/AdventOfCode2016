import org.junit.jupiter.api.Test
import java.io.File

class Day07 {
    private val ips = File("resources/07.txt").readLines()
    private val parts =
        ips.map { ip -> ip to ip.split('[', ']').withIndex().filter { it.index % 2 == 0 }.map { it.value }.toSet() }
            .toMap()
    private val hypernets =
        ips.map { ip -> ip to ip.split('[', ']').withIndex().filter { it.index % 2 == 1 }.map { it.value }.toSet() }
            .toMap()

    private fun Collection<String>.hasAbba(): Boolean = this.any { it.isAbba() }

    private fun CharSequence.isAbba(): Boolean {
        for (i in 0 until this.length - 3) {
            if (this[i] != this[i + 1] && this[i] == this[i + 3] && this[i + 1] == this[i + 2]) {
                return true
            }
        }
        return false
    }

    private fun isAbaAndBab(ip: String): Boolean {
        for (aba in parts[ip]!!.getAbas()) {
            for (bab in hypernets[ip]!!.getAbas()) {
                if (aba[0] == bab[1] && aba[1] == bab[0]) {
                    return true
                }
            }
        }
        return false
    }

    private fun Collection<String>.getAbas(): Set<String> = this.flatMap { it.getAbas() }.toSet()

    private fun CharSequence.getAbas(): Set<String> {
        val abas = mutableSetOf<String>()
        var i = -1
        while (++i < this.length - 2) {
            if (this[i] != this[i + 1] && this[i] == this[i + 2]) {
                abas.add(this.substring(i, i + 3))
                i += 2
            }
        }
        return abas
    }

    @Test
    fun part1() {
        println(ips.count { ip -> !hypernets[ip]!!.hasAbba() && parts[ip]!!.hasAbba() })
    }

    @Test
    fun part2() {
        println(ips.count { ip -> isAbaAndBab(ip) })
    }
}
