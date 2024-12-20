package days

import xyz.epicebic.aoc.util.GridPoint
import xyz.epicebic.aoc.util.grid

class Day20 : Day(20) {
    override fun partOne(inputLines: List<String>) = solve(inputLines, 2)
    override fun partTwo(inputLines: List<String>) = solve(inputLines, 20)

    private fun solve(input: List<String>, cheatDuration: Int): Int {
        val grid = input.grid()
        var next = grid.locateFirst('S')
        val end = grid.locateFirst('E')
        val path = mutableListOf(next)
        val visited = HashSet<GridPoint>()

        while (next != end) {
            visited.add(next)
            next = grid.getCardinalNeighbourPoints(next).first { grid.look(it)!! != '#' && it !in visited }
            path.add(next)
        }

        return (0..<path.lastIndex - 1).sumOf { i ->
            (i + 1..<path.size).count { j ->
                val dist = path[i].manhattanDistance(path[j])
                dist <= cheatDuration && j - i - dist >= 100
            }
        }
    }

}
