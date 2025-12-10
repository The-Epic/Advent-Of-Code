package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.Grid
import xyz.epicebic.aoc.util.GridPoint
import xyz.epicebic.aoc.util.grid

class Day7 : Day(7) {

    val SIDES = listOf(Direction.EAST, Direction.WEST)

    override fun partOne(inputLines: List<String>): Any {
        val grid = inputLines.grid()

        mutate(grid)

        val count = grid.locateAll('^').filter { pos ->
            grid.look(pos + Direction.NORTH) == '|'
        }.size


        return count
    }

    private fun mutate(grid: Grid<Char>) {
        grid.moveToFirst('S')
        grid.move(Direction.SOUTH)
        grid.set('|')

        for (row in 0 until grid.rows) {
            for (col in 0 until grid.cols) {
                val currentChar = grid.look(row, col)
                when (currentChar) {
                    '^' -> {
                        for (side in SIDES) {
                            val pos = GridPoint(row, col) + side
                            grid.set(pos, '|')
                        }
                    }

                    '.' -> {
                        val pos = GridPoint(row, col)
                        val northChar = grid.look(pos + Direction.NORTH)
                        if (northChar == '|') {
                            grid.set(pos, '|')
                        }
                    }
                }
            }
        }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = inputLines.grid()
        mutate(grid)


        val paths = Array(grid.rows) { LongArray(grid.cols) }
        val origin = grid.locateFirst('S')
        paths[origin.x][origin.y] = 1L

        for (x in 0 until grid.rows - 1) {
            for (y in 0 until grid.cols) {

                val active = paths[x][y]
                if (active == 0L) continue

                val curr = GridPoint(x, y)
                val ch = grid.look(curr)

                if (ch == 'S' || ch == '|') {
                    val down = curr + Direction.SOUTH
                    val dest = grid.look(down)
                    if (dest != null && dest != '.') {
                        paths[down.x][down.y] += active
                    }
                }

                if (ch == '^') {
                    for (side in SIDES) {
                        val target = curr + side + Direction.SOUTH
                        val dest = grid.look(target)
                        if (dest != null && dest != '.') {
                            paths[target.x][target.y] += active
                        }
                    }
                }
            }
        }

        return paths[grid.rows - 1].sum()
    }
}
