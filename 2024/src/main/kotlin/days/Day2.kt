package days

import kotlin.math.absoluteValue

class Day2 : Day(2) {

    override fun partOne(inputLines: List<String>): Any {
        val reports = inputLines.map { it.split(" ").map { it.toInt() } }

        return reports.count { it.isSafe() }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val reports = inputLines.map { it.split(" ").map { it.toInt() } }

        return reports.count {
            for (i in it.indices) {
                if (it.filterIndexed { index, _ -> index != i}.isSafe()) return@count true
            }
            return@count false
        }
    }

    private fun List<Int>.isSafe() =
        (this.sorted() == this || this.sortedDescending() == this) && this.zipWithNext()
            .all { (it.first - it.second).absoluteValue in 1..3 }
}