package days

import xyz.epicebic.aoc.util.GridPoint

class Day14 : Day(14) {

    private data class Robot(var point: GridPoint, var velocity: GridPoint)

    private fun parse(inputLines: List<String>) = inputLines.map { line ->
        extractNegativesSplit(line).let {
            Robot(
                GridPoint(it[0], it[1]),
                GridPoint(it[2], it[3])
            )
        }
    }

    private fun extractNegativesSplit(string: String) =
        string.split(Regex("[^-\\d]+")).filter { it.isNotBlank() }.map { it.toInt() }

    private infix fun Int.pm(other: Int): Int {
        val mod = this % other
        return if (mod < 0) mod + other else mod
    }

    override fun partOne(inputLines: List<String>): Any {
        val robots = parse(inputLines)

        val width = 101
        val height = 103

        val quadrants = MutableList(4) { 0 }

        val mx = width / 2
        val mz = height / 2

        for (robot in robots) {
            val (point, velocity) = robot

            val modifiedX = (point.x + 100 * velocity.x) pm width
            val modifiedZ = (point.y + 100 * velocity.y) pm height

            val newPoint = GridPoint(modifiedX, modifiedZ)

            quadrants[when {
                newPoint.x > mx && newPoint.y < mz -> 0
                newPoint.x < mx && newPoint.y < mz -> 1
                newPoint.x < mx && newPoint.y > mz -> 2
                newPoint.x > mx && newPoint.y > mz -> 3
                else -> continue
            }]++
        }

        return quadrants.reduce { a, b -> a * b }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val robots = parse(inputLines)

        val width = 101
        val height = 103

        var i = 0
        while (true) {
            i++

            for (robot in robots) {
                val (point, velocity) = robot

                val modifiedX = (point.x + velocity.x) pm width
                val modifiedZ = (point.y + velocity.y) pm height

                robot.point = GridPoint(modifiedX, modifiedZ)
            }

            if (robots.distinctBy { it.point }.size == robots.size) return i
        }
    }
}
