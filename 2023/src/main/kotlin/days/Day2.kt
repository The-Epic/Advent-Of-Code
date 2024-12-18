package days

class Day2 : Day(2) {

    private data class Selection(var red: Int, var green: Int, var blue: Int)
    private data class Game(val id: Int, val selection: List<Selection>)

    override fun partOne(inputLines: List<String>): Any {
        val games = parseInput(inputLines)

        return games.sumOf {
            val isPossible = it.selection.all { selection ->
                selection.red <= 12 && selection.green <= 13 && selection.blue <= 14
            }

            if (isPossible) it.id else 0
        }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val games = parseInput(inputLines)
        var totalPower = 0

        for (game in games) {
            val maxRed = game.selection.maxOf { it.red }
            val maxGreen = game.selection.maxOf { it.green }
            val maxBlue = game.selection.maxOf { it.blue }

            val power = maxRed * maxGreen * maxBlue
            totalPower += power
        }

        return totalPower
    }

    private fun parseInput(lines: List<String>): List<Game> {
        var gameId = 1
        val games = mutableListOf<Game>()

        for (line in lines) {
            val modifiedLine = line.substring(6 + gameId.toString().length)

            val selectionList = modifiedLine.split(';').map { game ->
                val selection = Selection(0, 0, 0)

                game.split(',').forEach { coloured ->
                    val (amountStr, colour) = coloured.trim().split(" ")
                    val amount = amountStr.toInt()

                    when (colour) {
                        "red" -> selection.red = amount
                        "green" -> selection.green = amount
                        "blue" -> selection.blue = amount
                    }
                }

                selection
            }

            games.add(Game(gameId, selectionList))
            gameId++
        }

        return games
    }
}
