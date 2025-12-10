package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.GridPoint
import xyz.epicebic.aoc.util.grid

class Day12 : Day(12) {

    private data class Region(val type: Char, var area: Int = 0, var outer: Int = 0)

    override fun partOne(inputLines: List<String>): Any {
        val grid = inputLines.grid()

        val visited = Array(grid.rows) { BooleanArray(grid.cols) }

        fun search(x: Int, z: Int, type: Char): Region {
            val stack = mutableListOf(GridPoint(x, z))
            val region = Region(type)

            while (stack.isNotEmpty()) {
                val current = stack.removeAt(stack.size - 1)
                val currentX = current.x
                val currentZ = current.y

                if (visited[currentX][currentZ] || grid.look(currentX, currentZ) != type) continue

                visited[currentX][currentZ] = true
                region.area++

                for (direction in Direction.CARDINAL_DIRECTIONS) {
                    val nextX = currentX + direction.x
                    val nextZ = currentZ + direction.y
                    val neighbour = grid.look(nextX, nextZ)
                    if (neighbour == type && (visited.getOrNull(nextX)?.getOrNull(nextZ) == false)) {
                        stack.add(GridPoint(nextX, nextZ))
                    } else if (neighbour != type) {
                        region.outer++
                    }
                }
            }

            return region
        }

        val regions = mutableListOf<Region>()

        for (x in 0 until grid.rows) {
            for (z in 0 until grid.cols) {
                if (!visited[x][z]) {
                    regions.add(search(x, z, grid.look(x, z)!!))
                }
            }
        }

//        val totalPrice =
        return regions.sumOf { it.area * it.outer }
    }


    override fun partTwo(inputLines: List<String>): Any {
        return "Unsolved"
    }
}
