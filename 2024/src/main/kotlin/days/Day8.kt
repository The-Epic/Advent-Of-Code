package days

import xyz.epicebic.aoc.util.Grid

class Day8 : Day(8) {
    override fun partOne(inputLines: List<String>): Any {
        val grid = Grid.create(inputLines)

        val positions = mutableListOf<Pair<Int, Int>>()
        for (row in 0 until grid.rows) {
            for (col in 0 until grid.cols) {
                val char = grid.look(row, col)
                if (char?.isLetterOrDigit() == true) {
                    positions.add(row to col)
                }
            }
        }

        return positions.groupBy { (row, col) -> grid.look(row, col)!!.code }.flatMap { (_, group) ->
            val pairs = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

            for (i in group.indices) {
                for (j in i + 1 until group.size) {
                    pairs.add(group[i] to group[j])
                    pairs.add(group[j] to group[i])
                }
            }

            pairs.flatMap { (a, b) ->
                val newPoint = Pair(a.first + a.first - b.first, a.second + a.second - b.second)

                if (newPoint.first in 0 until grid.rows && newPoint.second in 0 until grid.cols) {
                    sequenceOf(newPoint)
                } else {
                    emptySequence()
                }
            }
        }.distinct().size
    }

    override fun partTwo(inputLines: List<String>): Any {
        val grid = Grid.create(inputLines)

        val positions = mutableListOf<Pair<Int, Int>>()
        for (row in 0 until grid.rows) {
            for (col in 0 until grid.cols) {
                val char = grid.look(row, col)
                if (char?.isLetterOrDigit() == true) {
                    positions.add(row to col)
                }
            }
        }

        return positions.groupBy { (row, col) -> grid.look(row, col)!!.code }.flatMap { (_, group) ->
            val pairs = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

            for (i in group.indices) {
                for (j in i + 1 until group.size) {
                    pairs.add(group[i] to group[j])
                    pairs.add(group[j] to group[i])
                }
            }

            pairs.flatMap { (a, b) ->
                generateSequence(Pair(a.first, a.second)) { prev ->
                    val nextPoint = Pair(prev.first + a.first - b.first, prev.second + a.second - b.second)

                    if (nextPoint.first in 0 until grid.rows && nextPoint.second in 0 until grid.cols) {
                        nextPoint
                    } else {
                        null
                    }
                }
            }
        }.distinct().size
    }
}
