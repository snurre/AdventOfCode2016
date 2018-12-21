import org.junit.jupiter.api.Test
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

class Day232 {
    private val register = arrayOf(Register(7), Register(), Register(), Register())
    private val index = AtomicInteger()
    private val instructions: List<Instruction>

    init {
        instructions = arrayListOf()
        File("resources/23.txt").readLines().forEach { s ->
            val p = s.split(' ')
            val cmd = Command.valueOf(p[0])
            var from: Register? = null
            var value = 0
            if (p[1][0].isLetter()) {
                from = register[p[1][0] - 'a']
            } else {
                value = p[1].toInt()
            }
            var to: Register? = null
            var mult: Register? = null
            var offset = 0
            if (p.size == 3) {
                if (p[2][0].isLetter()) {
                    to = register[p[2][0] - 'a']
                } else {
                    offset = p[2].toInt()
                }
            } else if (p.size == 4) {
                to = register[p[2][0] - 'a']
                mult = register[p[3][0] - 'a']
            }
            instructions.add(Instruction(cmd, from, to, mult, value, offset, index, instructions))
        }
    }

    data class Register(var value: Int = 0)
    enum class Command(val func: (List<Instruction>, Int, Int?) -> Int) {
        cpy({ i, x, y -> x }),
        inc({ i, x, y -> x + 1 }),
        dec({ i, x, y -> x - 1 }),
        jnz({ i, x, y -> if (x == 0) x + 1 else x + y!! }),
        tgl({ i, x, y ->
            if (x + y!! in 0 until i.size) {
                i[x + y].command = i[x + y].command.opposite
            }
            0
        }),
        mul({ i, x, y -> x * y!! })
        ;

        val opposite by lazy {
            when (this) {
                cpy -> Command.jnz
                inc -> Command.dec
                dec -> Command.inc
                jnz -> Command.cpy
                tgl -> Command.inc
                mul -> Command.jnz
            }
        }
    }

    data class Instruction(
        var command: Command,
        val from: Register?,
        val to: Register?,
        val mult: Register?,
        val value: Int,
        var offset: Int,
        val index: AtomicInteger,
        val instructions: List<Instruction>
    ) {
        fun execute() {
            when (command) {
                Command.cpy -> {
                    if (to != null) {
                        if (from != null) {
                            to.value = command.func.invoke(instructions, from.value, null)
                        } else {
                            to.value = command.func.invoke(instructions, value, null)
                        }
                    }
                    index.incrementAndGet()
                }
                Command.inc, Command.dec -> {
                    from!!.value = command.func.invoke(instructions, from.value, null)
                    index.incrementAndGet()
                }
                Command.jnz -> {
                    if (to != null) offset = to.value
                    if (from != null) {
                        index.set(command.func.invoke(instructions, value, offset))
                    } else {
                        index.set(command.func.invoke(instructions, from!!.value, offset))
                    }
                }
                Command.tgl -> {
                    command.func.invoke(instructions, from!!.value, index.getAndIncrement())
                }
                Command.mul -> {
                    mult!!.value = command.func.invoke(instructions, from!!.value, to!!.value)
                    index.addAndGet(6)
                }
            }
        }
    }

    @Test
    fun part1() {
        while (index.get() < instructions.size) {
            val i = index.get()
            val instr = instructions[index.get()]
            instr.execute()
            println("$i\t${register.map { it.value.toString().padStart(8) }.joinToString(" ")}")
        }
        println(register[0])
    }

}
