package days

import xyz.epicebic.aoc.util.Grid

class Day6 : Day(6) {
    override fun partOne(inputLines: List<String>): Any {
        val grid = inputLines.subList(0, 4).map { it.split(" ").filterNot { it.isBlank() } }
            .map { row -> row.map { it.toInt() }.toMutableList() }.toMutableList()
            .let { Grid(it) }

        val operations = inputLines[4].map { it }.filter { it != ' ' }


        var sum = 0L
        for (col in 0 until grid.cols) {
            val numbers = mutableListOf<Long>()
            for (row in 0 until grid.rows) {
                numbers.add(grid.look(row, col)?.toLong() ?: 0L)
            }

            val operator = operations[col]
            val result = when (operator) {
                '+' -> numbers.sum()
                '*' -> numbers.reduce { acc, i -> acc * i }
                else -> 0L
            }

            sum += result
        }

        return sum
    }

    override fun partTwo(inputLines: List<String>): Any {
        return "Unsolved!"
    }
}
