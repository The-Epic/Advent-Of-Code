package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.grid

class Day4 : Day(4) {

    private val ROLL = '@'
    private val AIR = '.'

    override fun partOne(inputLines: List<String>): Any {
        val grid = inputLines.grid()

        val sum = grid.locateAll(ROLL).filter {
            grid.collectAround(it, Direction.entries).filter { pos -> grid.look(pos) == ROLL }.size < 4
        }.size


        return sum
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = inputLines.grid()

        var sum = 0
        var lastSum = 0
        do {
            val search = grid.locateAll(ROLL).filter {
                grid.collectAround(it, Direction.entries).filter { pos -> grid.look(pos) == ROLL }.size < 4
            }

            lastSum = search.size
            sum += lastSum
            for (pos in search) {
                grid.set(pos, AIR)
            }
        } while (lastSum != 0)

        return sum
    }
}
