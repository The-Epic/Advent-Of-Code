package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.Grid
import xyz.epicebic.aoc.util.GridPoint
import xyz.epicebic.aoc.util.grid
import java.util.*


class Day16 : Day(16) {

    private val start = 'S'
    private val end = 'E'
    private val wall = '#'

    override fun partOne(inputLines: List<String>): Any {

        val grid = inputLines.grid()

        val start = grid.locateFirst(start)
        val end = grid.locateFirst(end)

        val result = findShortestPath(grid, start, Direction.EAST, end)

        return result
    }

    override fun partTwo(inputLines: List<String>): Any {
        return "Unsolved"
    }

    private fun findShortestPath(grid: Grid<Char>, start: GridPoint, startDirection: Direction, end: GridPoint): Int {
        val dist = mutableMapOf<Pair<GridPoint, Direction>, Int>()
        val pq = PriorityQueue<Triple<GridPoint, Direction, Int>>(compareBy { it.third })

        Direction.CARDINAL_DIRECTIONS.forEach {
            dist[Pair(start, it)] = Int.MAX_VALUE
        }
        dist[Pair(start, startDirection)] = 0

        pq.add(Triple(start, startDirection, 0))

        while (pq.isNotEmpty()) {
            val (currentPos, currentDir, currentDist) = pq.poll()

            if (currentPos == end) {
                return currentDist
            }

            val nextPos = GridPoint(currentPos.x + currentDir.x, currentPos.z + currentDir.z)
            if (isValidMove(grid, nextPos)) {
                val nextState = Pair(nextPos, currentDir)
                val nextDist = currentDist + 1
                if (nextDist < (dist[nextState] ?: Int.MAX_VALUE)) {
                    dist[nextState] = nextDist
                    pq.add(Triple(nextPos, currentDir, nextDist))
                }
            }

            val nextDirClockwise = currentDir.nextClockwise
            val nextStateClockwise = Pair(currentPos, nextDirClockwise)
            val nextDistClockwise = currentDist + 1000
            if (nextDistClockwise < (dist[nextStateClockwise] ?: Int.MAX_VALUE)) {
                dist[nextStateClockwise] = nextDistClockwise
                pq.add(Triple(currentPos, nextDirClockwise, nextDistClockwise))
            }

            val nextDirCounterClockwise = currentDir.nextClockwise.nextClockwise.nextClockwise
            val nextStateCounterClockwise = Pair(currentPos, nextDirCounterClockwise)
            val nextDistCounterClockwise = currentDist + 1000
            if (nextDistCounterClockwise < (dist[nextStateCounterClockwise] ?: Int.MAX_VALUE)) {
                dist[nextStateCounterClockwise] = nextDistCounterClockwise
                pq.add(Triple(currentPos, nextDirCounterClockwise, nextDistCounterClockwise))
            }
        }

        // If we haven't found a path, return a large value (or error)
        return Int.MAX_VALUE
    }

    /**
     * Helper function to check if a position is valid (not a wall '#')
     */
    private fun isValidMove(grid: Grid<Char>, point: GridPoint): Boolean {
        return grid.look(point) != '#'
    }
}
