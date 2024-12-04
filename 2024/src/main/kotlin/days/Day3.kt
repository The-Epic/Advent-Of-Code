package days

class Day3 : Day(3) {

    private val MUL_REGEX = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)")
    private val PART_TWO_MUL = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)|do\\(\\)|don't\\(\\)")

    override fun partOne(inputLines: List<String>): Any {
        val input = inputLines[0]
        val tokens = MUL_REGEX.findAll(input).sortedBy { it.range.first }

        val matches = mutableListOf<Pair<Int, Int>>()
        for (token in tokens) {
            val (left, right) = MUL_REGEX.find(token.value)!!.destructured
            matches.add(Pair(left.toInt(), right.toInt()))
        }

        var finalNumber = 0
        for (mul in matches) {
            finalNumber += (mul.first * mul.second)
        }

        return finalNumber
    }

    override fun partTwo(inputLines: List<String>): Any {
        val input = inputLines[0]
        val tokens =
            PART_TWO_MUL.findAll(input).sortedBy { it.range.first } // + DO_REGEX.findAll(it) + DONT_REGEX.findAll(it)

        val matches = mutableListOf<Pair<Int, Int>>()
        var enabled = true
        for (token in tokens) {
            val value = token.value
            if (value == "do()") {
                enabled = true
            } else if (value == "don't()") {
                enabled = false
            } else {
                if (enabled) {
                    val (left, right) = PART_TWO_MUL.find(value)!!.destructured
                    matches.add(Pair(left.toInt(), right.toInt()))
                }
            }

        }

        var finalNumber = 0
        for (mul in matches) {
            finalNumber += (mul.first * mul.second)
        }

        return finalNumber
    }
}