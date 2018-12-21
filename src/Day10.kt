import org.junit.jupiter.api.Test
import java.io.File

class Day10 {
    open class Thing(val id: Int) {
        val chips = mutableListOf<Int>()
        open fun addChip(chip: Int) {
            chips.add(chip)
        }
    }

    class Bot(id: Int) : Thing(id) {
        var lowTarget: Thing? = null
        var highTarget: Thing? = null

        override fun addChip(chip: Int) {
            chips.add(chip)
            give()
        }

        fun give() {
            if (chips.size < 2) {
                return
            }
            val min = chips.min()!!
            val max = chips.max()!!
            if (min == 17 && max == 61) {
                println(id)
            }
            chips.clear()
            lowTarget!!.addChip(min)
            highTarget!!.addChip(max)
        }
    }

    private val bots = mutableMapOf<Int, Bot>()
    private val bins = mutableMapOf<Int, Thing>()

    init {
        File("resources/10.txt").useLines {
            it.forEach { s ->
                val p = s.split(' ')
                when (p[0]) {
                    "value" -> bots.computeIfAbsent(p[5].toInt()) { Bot(p[5].toInt()) }.chips.add(p[1].toInt())
                    "bot" -> {
                        val bot = bots.computeIfAbsent(p[1].toInt()) { Bot(p[1].toInt()) }
                        if (p[5] == "output") {
                            bot.lowTarget = bins.computeIfAbsent(p[6].toInt()) { Thing(p[6].toInt()) }
                        } else {
                            bot.lowTarget = bots.computeIfAbsent(p[6].toInt()) { Bot(p[6].toInt()) }
                        }
                        if (p[10] == "output") {
                            bot.highTarget = bins.computeIfAbsent(p[11].toInt()) { Thing(p[11].toInt()) }
                        } else {
                            bot.highTarget = bots.computeIfAbsent(p[11].toInt()) { Bot(p[11].toInt()) }
                        }
                    }
                }
            }
            for (bot in bots.values) bot.give()
        }
    }

    @Test
    fun part1() {
    }

    @Test
    fun part2() {
        println(bins[0]!!.chips[0] * bins[1]!!.chips[0] * bins[2]!!.chips[0])
    }
}
