package days

class Day4 : Day(4) {

    override fun partOne(inputLines: List<String>): Any {

        val xmasCoords = listOf(
            // line to column

            // straight forward
            listOf(
                0 to 0,
                0 to 1,
                0 to 2,
                0 to 3
            ),

            // straight backward
            listOf(
                0 to 0,
                0 to -1,
                0 to -2,
                0 to -3
            ),

            // straight up
            listOf(
                0 to 0,
                1 to 0,
                2 to 0,
                3 to 0
            ),

            // straight down
            listOf(
                0 to 0,
                -1 to 0,
                -2 to 0,
                -3 to 0
            ),

            // diagonal up left
            listOf(
                0 to 0,
                -1 to -1,
                -2 to -2,
                -3 to -3
            ),

            // diagonal down left
            listOf(
                0 to 0,
                1 to -1,
                2 to -2,
                3 to -3
            ),

            // diagonal up right
            listOf(
                0 to 0,
                -1 to 1,
                -2 to 2,
                -3 to 3
            ),

            // diagonal down right
            listOf(
                0 to 0,
                1 to 1,
                2 to 2,
                3 to 3
            )
        )

        var xmasCounter = 0
        inputLines.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { columnIndex, column ->
                if (column != 'X') return@forEachIndexed

                xmasCoords.forEach { coord ->
                    val xPoint  = lineIndex to columnIndex
                    val word = coord.map { pair ->
                        inputLines.getChar(xPoint, pair)
                    }.joinToString("")

                    if (word == "XMAS") {
                        xmasCounter++
                    }
                }
            }
        }
        return xmasCounter
    }

    /*
    M.M
    .A.
    S.S

    S.S
    .A.
    M.M

    M.S
    .A.
    M.S

    S.M
    .A.
    S.M
     */

    override fun partTwo(inputLines: List<String>): Any {
        val xMasCords = listOf(
            // line to column, designed to create masmas output

            // masmas down
            listOf(
                -1 to -1,
                0 to 0,
                1 to 1,

                -1 to 1,
                0 to 0,
                1 to -1
            ),

            // masmas up
            listOf(
                1 to -1,
                0 to 0,
                -1 to 1,

                1 to 1,
                0 to 0,
                -1 to -1
            ),

            // massam up
            listOf(
                -1 to -1,
                0 to 0,
                1 to 1,

                1 to -1,
                0 to 0,
                -1 to 1
            ),

            // sammas down
            listOf(
                -1 to 1,
                0 to 0,
                1 to -1,

                1 to 1,
                0 to 0,
                -1 to -1
            )
        )

        var xmasCounter = 0
        inputLines.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { columnIndex, column ->
                if (column != 'A') return@forEachIndexed

                xMasCords.forEach { coord ->
                    val xPoint  = lineIndex to columnIndex
                    val word = coord.map { pair ->
                        inputLines.getChar(xPoint, pair)
                    }.joinToString("")

                    if (word == "MASMAS") {
                        xmasCounter++
                    }
                }
            }
        }
        return xmasCounter
    }


    //inputLines[row][column]
    private fun List<String>.getChar(x: Pair<Int, Int>, offset: Pair<Int, Int>): Char = try {
        this[x.first + offset.first][x.second + offset.second]
    } catch (_: Exception) {
        ' '
    }
}