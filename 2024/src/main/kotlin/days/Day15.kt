package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.Grid
import xyz.epicebic.aoc.util.GridPoint
import xyz.epicebic.aoc.util.grid

class Day15 : Day(15) {
    private val wall = '#'
    private val box = 'O'
    private val robot = '@'
    private val air = '.'

    private fun parse(lines: List<String>, double: Boolean): Pair<Grid<Char>, List<Direction>> {
        val index = lines.indexOf("")
        val grid = lines.subList(0, index).let {
            if (double) it.map { it.replace("#", "##").replace("O", "[]").replace(".", "..").replace("@", "@.") }
            else it
        }
        val directions = lines.subList(index + 1, lines.size)

        return grid.grid() to directions.flatMap { it.toCharArray().map { Direction.directionFrom(it) } }
    }

    override fun partOne(inputLines: List<String>): Any {
        val (grid, directions) = parse(inputLines, false)

        grid.moveToFirst(robot)
        grid.set('.')

        for (direction in directions) {
            val look = grid.look(direction) ?: continue
            if (look == air) {
                grid.move(direction)
            }
            if (look == wall) continue
            if (look == box) {
                var ahead = grid.currentPos + direction + direction
                var pushBoxes = false
                val lookedAt = mutableListOf(grid.currentPos + direction, ahead)
                while (true) {
                    if (ahead.x < 0 || ahead.x >= grid.rows || ahead.y < 0 || ahead.y >= grid.cols) break

                    if (grid.look(ahead) == wall) {
                        break
                    }

                    if (grid.look(ahead) == air) {
                        pushBoxes = true
                        break
                    }

                    ahead += direction
                    lookedAt.add(ahead)
                }

                if (pushBoxes) {
                    for ((a, b) in lookedAt.reversed().zipWithNext()) {
                        val aChar = grid.look(a)!!
                        val bChar = grid.look(b)!!
                        grid.set(a, bChar)
                        grid.set(b, aChar)
                    }

                    grid.move(direction)
                }
            }
        }


        return grid.locateAll(box).sumOf {
            (100 * it.x) + it.y
        }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val (grid, directions) = parse(inputLines, true)

        grid.moveToFirst(robot)
        grid.set('.')

        for (direction in directions) {
            val look = grid.look(direction) ?: continue

            when (look) {
                '.' -> {
                    grid.move(direction)
                }
                '#' -> continue
                '[', ']' -> {
                    when (direction) {
                        Direction.WEST, Direction.EAST -> {
                            var ahead = grid.currentPos + direction + direction
                            var pushBoxes = false
                            val lookedAt = mutableListOf(grid.currentPos + direction, ahead)
                            while (true) {
                                if (ahead.x < 0 || ahead.x >= grid.rows || ahead.y < 0 || ahead.y >= grid.cols) break

                                if (grid.look(ahead) == wall) {
                                    break
                                }

                                if (grid.look(ahead) == air) {
                                    pushBoxes = true
                                    break
                                }

                                ahead += direction
                                lookedAt.add(ahead)
                            }

                            if (pushBoxes) {
                                for ((a, b) in lookedAt.reversed().zipWithNext()) {
                                    val aChar = grid.look(a)!!
                                    val bChar = grid.look(b)!!
                                    grid.set(a, bChar)
                                    grid.set(b, aChar)
                                }

                                grid.set('.')
                                grid.move(direction)
                                grid.set('@')
                            }
                        }
                        Direction.NORTH, Direction.SOUTH -> {
                            data class Box(val leftPoint: GridPoint, val rightPoint: GridPoint, val direction: Direction)

                            fun createBox(part: GridPoint): Box {
                                val char = grid.look(part)!!

                                return if (char == '[') {
                                    Box(part,part + Direction.EAST, direction)
                                } else {
                                    Box(part + Direction.WEST, part, direction)
                                }
                            }

                            val originalBox = createBox(grid.currentPos + direction)
                            val movedBoxes = mutableListOf(originalBox)
                            var wallFound = false

                            var index = 0
                            while (true) {
                                if (index >= movedBoxes.size) break
                                val box = movedBoxes[index]
                                if (grid.look(box.leftPoint) == '.' && grid.look(box.rightPoint) == '.') continue

                                val leftChar = grid.look(box.leftPoint + direction)
                                val rightChar = grid.look(box.rightPoint + direction)

                                if (leftChar  == wall || rightChar == wall) {
                                    wallFound = true
                                    break
                                }

                                if (leftChar == '.' && rightChar == '.') {
                                    index++
                                    continue
                                }

                                if (leftChar == '[' || leftChar == ']') {
                                    movedBoxes.add(createBox(box.leftPoint + direction))
                                }

                                if (rightChar == '[' || rightChar == ']') {
                                    movedBoxes.add(createBox(box.rightPoint + direction))
                                }

                                index++
                            }

                            if (wallFound) {
                                continue
                            }

                            for ((left, right, direction) in movedBoxes.distinctBy { it.leftPoint }.distinctBy { it.rightPoint }.reversed()) {
                                val leftAbove = left + direction
                                val rightAbove = right + direction

                                val currentLeft = grid.look(left)!!
                                val currentRight = grid.look(right)!!

                                val currentAboveLeft = grid.look(leftAbove)!!
                                val currentAboveRight = grid.look(rightAbove)!!

                                grid.set(leftAbove, currentLeft)
                                grid.set(rightAbove, currentRight)

                                grid.set(left, currentAboveLeft)
                                grid.set(right, currentAboveRight)
                            }

                            grid.move(direction)
                        }
                        else -> {}
                    }
                }
            }
        }


        val boxes = mutableListOf<Pair<GridPoint, GridPoint>>()

        grid.locateAll('[').map {
            boxes.add(it to it + Direction.EAST)
        }

        return grid.locateAll('[').sumOf {
            (100 * it.x) + it.y
        }
    }
}
