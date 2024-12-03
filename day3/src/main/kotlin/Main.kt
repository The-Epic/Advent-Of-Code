import xyz.epicebic.aoc.util.readResourceLines

private val MUL_REGEX = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)")
private val PART_TWO_MUL = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)|do\\(\\)|don't\\(\\)")
//private val DO_REGEX = Regex("do\\(\\)")
//private val DONT_REGEX = Regex("don't\\(\\)")

fun main() {
    val inputLines = readResourceLines("input.txt").joinToString()
    println(partOne(inputLines))
    println(partTwo(inputLines))
}

fun partOne(input: String): Any {
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

fun partTwo(input: String): Any {
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