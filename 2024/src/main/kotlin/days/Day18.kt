package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.Grid
import xyz.epicebic.aoc.util.GridPoint
import java.util.*

class Day18 : Day(18) {
    override fun partOne(inputLines: List<String>): Any {
        val grid = Grid(71, 71, 0, 0, MutableList(71) { MutableList(71) { '.' } })

        val bytePoints = parse(inputLines)
        for (point in bytePoints.subList(0, 1024)) {
            grid.set(point, '#')
        }

        return bfs(grid, GridPoint(0, 0), GridPoint(70, 70))
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = Grid(71, 71, 0, 0, MutableList(71) { MutableList(71) { '.' } })

        val bytePoints = parse(inputLines)
        val start = GridPoint(0, 0)
        val end = GridPoint(70, 70)

        for (point in bytePoints) {
            grid.set(point, '#')

            val pathExists = bfs(grid, start, end) != 0

            if (!pathExists) {
                return "${point.x},${point.y}"
            }
        }

        return "Path never blocked"
    }

    private fun parse(inputLines: List<String>): List<GridPoint> {
        return inputLines.map {
            val split = it.split(',')
            GridPoint(split.first().toInt(), split.last().toInt())
        }
    }

    private fun bfs(grid: Grid<Char>, start: GridPoint, end: GridPoint): Int {
        val queue: ArrayDeque<Pair<GridPoint, Int>> = ArrayDeque()
        queue.add(Pair(start, 0))

        val visited = mutableSetOf<GridPoint>()
        visited.add(start)

        while (queue.isNotEmpty()) {
            val (current, steps) = queue.removeFirst()

            if (current == end) return steps
            for (direction in Direction.CARDINAL_DIRECTIONS) {
                val neighbor = current + direction

                if (neighbor.x in 0 until grid.rows && neighbor.y in 0 until grid.cols &&
                    grid.look(neighbor) == '.' && neighbor !in visited) {

                    queue.add(Pair(neighbor, steps + 1))
                    visited.add(neighbor)
                }
            }
        }

        return 0
    }
}
