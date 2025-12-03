package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.Grid
import xyz.epicebic.aoc.util.GridPoint

class Day6 : Day(6) {

    private val startChar = '^'
    private val wallChar = '#'
    private val visited = mutableSetOf<GuardPosition>()


    override fun partOne(inputLines: List<String>): Any {
        val grid = Grid.create(inputLines)

        return run(grid, false)
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = Grid.create(inputLines)
        var counter = 0


        for ((location, direction) in visited) {
            val x = location.x
            val z = location.y
            val copy = grid.clone().move(x, z)
            val current = copy.currentChar()
            if (current == startChar || current == wallChar) continue
            copy.set(wallChar)

            if (run(copy, true) == 1) counter++
        }

        return counter
    }

    private fun run(grid: Grid<Char>, looping: Boolean): Int {
        grid.moveToFirst(startChar)

        var direction = Direction.NORTH

        val walkedLocations = mutableSetOf<GuardPosition>()
        walkedLocations.add(GuardPosition(grid.currentPos, direction))

        while (true) {
            var nextChar = grid.look(direction)

            if (nextChar == null) break
            else while (nextChar == wallChar) {
                direction = direction.nextClockwise
                nextChar = grid.look(direction)
            }

            grid.move(direction)

            val currentLocation = GuardPosition(grid.currentPos, direction)
            if (looping && walkedLocations.contains(currentLocation)) {
                return 1
            } else {
                walkedLocations.add(currentLocation)
            }
        }

        val walkedDistinct = walkedLocations.distinctBy { it.location }
        if (!looping) visited.addAll(walkedDistinct)
        return if (looping) 0 else walkedDistinct.count()
    }

    private data class GuardPosition(val location: GridPoint, val direction: Direction)
}
