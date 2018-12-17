import org.junit.jupiter.api.Test
import kotlin.math.min

class Day11 {
    private fun solve(floors: Array<Int>): Int {
        var moveCount = 0
        val totalPieces = floors.sum()
        var elevatorPieces = min(floors[1], 2)
        floors[1] -= elevatorPieces
        var currentFloor = 1
        while (floors[4] + 1 != totalPieces) {
            while (elevatorPieces < 2 && currentFloor > 1) {
                currentFloor--
                val piecesTaken = min(floors[currentFloor], 2 - elevatorPieces)
                if (piecesTaken > 0) {
                    elevatorPieces += piecesTaken
                    floors[currentFloor] -= piecesTaken
                }
                moveCount++
            }
            while (currentFloor < 4) {
                currentFloor++
                val piecesTaken = min(floors[currentFloor], 2 - elevatorPieces)
                if (piecesTaken > 0) {
                    elevatorPieces += piecesTaken
                    floors[currentFloor] -= piecesTaken
                }
                moveCount++
            }
            floors[4] += 1
            elevatorPieces--
        }
        return moveCount
    }

    @Test
    fun part1() {
        println(solve(arrayOf(0, 4, 5, 1, 0)))
    }

    @Test
    fun part2() {
        println(solve(arrayOf(0, 8, 5, 1, 0)))
    }
}
