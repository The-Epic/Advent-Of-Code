package days

import xyz.epicebic.aoc.util.GridPoint

class Day9 : Day(9) {
    override fun partOne(inputLines: List<String>): Any {
        val gridPoints = inputLines.map {
            val (a, b) = it.split(",").map { n -> n.toInt() }
            GridPoint(a, b)
        }

        var largestDistance = 0L
        for (x in gridPoints) {
            for (y in gridPoints) {
                if (x != y) {
                    val distance = x.area(y)
                    if (distance > largestDistance) {
                        largestDistance = distance
                    }
                }
            }
        }

        return largestDistance
    }

    override fun partTwo(inputLines: List<String>): Any {
        return "Unsolved!"
    }
}
