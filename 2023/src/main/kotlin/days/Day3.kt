package days

import xyz.epicebic.aoc.util.Direction
import xyz.epicebic.aoc.util.GridPoint
import xyz.epicebic.aoc.util.grid

class Day3 : Day(3) {
    override fun partOne(inputLines: List<String>): Any {
        val grid = inputLines.grid()

        var output = 0
        for (x in 0..grid.rows) {
            for (z in 0..grid.cols) {
                val point = GridPoint(x, z)
                val char = grid.look(point)

                if (char == null || char.isDigit() || char == '.') continue

                val digitsFound = mutableListOf<List<GridPoint>>()
                for (direction in Direction.entries) {
                    val directionLook = point + direction
                    val directionChar = grid.look(directionLook)!!

                    if (directionChar == '.') continue
                    val digitToPoint = mutableMapOf(directionLook to directionChar)

                    var eastLook = directionLook + Direction.EAST
                    while (grid.look(eastLook)?.isDigit() == true) {
                        digitToPoint[eastLook] = grid.look(eastLook)!!
                        eastLook += Direction.EAST
                    }

                    var westLook = directionLook + Direction.WEST
                    while (grid.look(westLook)?.isDigit() == true) {
                        digitToPoint[westLook] = grid.look(westLook)!!
                        westLook += Direction.WEST
                    }

                    val sorted = digitToPoint.toList().sortedBy { it.first }
                    val mapped = sorted.map { it.first }
                    val already = digitsFound.contains(mapped)

                    if (already) continue
                    digitsFound.add(mapped)

                    val number = sorted.map { it.second }.joinToString("").toInt()
                    output += number
                }
            }
        }

        return output
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = inputLines.grid()

        var output = 0
        grid.locateAll('*').forEach {
            val starNumbers = mutableListOf<Int>()
            val digitsFound = mutableListOf<List<GridPoint>>()
            for (direction in Direction.entries) {
                val directionLook = it + direction
                val directionChar = grid.look(directionLook)!!

                if (directionChar == '.') continue
                val digitToPoint = mutableMapOf(directionLook to directionChar)

                var eastLook = directionLook + Direction.EAST
                while (grid.look(eastLook)?.isDigit() == true) {
                    digitToPoint[eastLook] = grid.look(eastLook)!!
                    eastLook += Direction.EAST
                }

                var westLook = directionLook + Direction.WEST
                while (grid.look(westLook)?.isDigit() == true) {
                    digitToPoint[westLook] = grid.look(westLook)!!
                    westLook += Direction.WEST
                }

                val sorted = digitToPoint.toList().sortedBy { it.first }
                val mapped = sorted.map { it.first }
                val already = digitsFound.contains(mapped)

                if (already) continue
                digitsFound.add(mapped)

                val number = sorted.map { it.second }.joinToString("").toInt()
                starNumbers.add(number)
            }

            if (starNumbers.size < 2) return@forEach

            val multiplied = starNumbers.first() * starNumbers.last()
            output += multiplied
        }

        return output
    }
}
