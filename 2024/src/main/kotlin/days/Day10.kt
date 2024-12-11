package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.Grid
import xyz.epicebic.aoc.util.GridPoint

class Day10 : Day(10) {

    private val trail = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    override fun partOne(inputLines: List<String>): Any {
        val grid = Grid.create(inputLines)

        val trailHeads = grid.locateAll('0')

        var counter = 0

        for (head in trailHeads) {
            val previousPoints = mutableSetOf(head)
            val nextPoints = mutableSetOf<GridPoint>()

            for (next in trail) {
                for (previous in previousPoints) {
                    nextPoints += nextPoints(previous, next, grid)
                }

                previousPoints.clear()
                previousPoints.addAll(nextPoints)
                nextPoints.clear()
            }

            counter += previousPoints.size
        }
        return counter
    }

    private fun nextPoints(previous: GridPoint, next: Char, grid: Grid): List<GridPoint> {
        val clone = grid.clone()
        val found = mutableListOf<GridPoint>()
        clone.move(previous)

        for (direction in Direction.CARDINAL_DIRECTIONS) {
            if (clone.look(direction) == next) {
                found.add(previous + direction)
            }
        }

        return found
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = Grid.create(inputLines)

        val trailHeads = grid.locateAll('0')

        return trailHeads.sumOf { scoreTrailHead(it, trail, grid) }
    }

    private fun scoreTrailHead(point: GridPoint, trail: List<Char>, grid: Grid): Long = if (trail.size == 1) {
        nextPoints(point, trail[0], grid).count().toLong()
    } else {
        nextPoints(point, trail[0], grid).sumOf { scoreTrailHead(it, trail.subList(1, trail.size), grid) }
    }
}
