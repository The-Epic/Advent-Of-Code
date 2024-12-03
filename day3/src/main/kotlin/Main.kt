import xyz.epicebic.aoc.util.readResourceLines
import java.util.regex.Pattern

private val MUL_REGEX = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)")
private val PART_TWO_MUL = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)|do\\(\\)|don't\\(\\)")
//private val DO_REGEX = Regex("do\\(\\)")
//private val DONT_REGEX = Regex("don't\\(\\)")

fun main() {
    val inputLines = readResourceLines("input.txt")
    val demoInput = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    println(partOne(inputLines))
    println(partTwo(inputLines))
}

fun partOne(inputLines: List<String>): Any {
    val mulPairs = inputLines.flatMap {

        val tokens = MUL_REGEX.findAll(it).sortedBy { it.range.first }

        val matches = mutableListOf<Pair<Int, Int>>()
        for (token in tokens) {
            val (left, right) = MUL_REGEX.find(token.value)!!.destructured
            matches.add(Pair(left.toInt(), right.toInt()))
        }

        return@flatMap matches
    }

    var finalNumber = 0
    for (mul in mulPairs) {
        finalNumber += (mul.first * mul.second)
    }

    return finalNumber
}

fun partTwo(inputLines: List<String>): Any {
    val mulPairs = inputLines.flatMap {

        val tokens =
            PART_TWO_MUL.findAll(it).sortedBy { it.range.first } // + DO_REGEX.findAll(it) + DONT_REGEX.findAll(it)

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

        return@flatMap matches
    }

    var finalNumber = 0
    for (mul in mulPairs) {
        finalNumber += (mul.first * mul.second)
    }

    return finalNumber
}