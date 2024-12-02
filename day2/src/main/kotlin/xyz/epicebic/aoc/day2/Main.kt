package xyz.epicebic.aoc.day2

import xyz.epicebic.aoc.util.readResourceLines
import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val inputLines = readResourceLines("input.txt")
    println(partOne(inputLines))
    //listOf("7 6 4 2 1", "1 2 7 8 9", "9 7 6 2 1", "1 3 2 4 5", "8 6 4 4 1", "1 3 6 7 9")
    println(partTwo(inputLines))
}

fun partOne(inputLines: List<String>): Any {
    val reports = inputLines.map { it.split(" ").map { it.toInt() } }

    return reports.count { it.isSafe() }
}

fun partTwo(inputLines: List<String>): Any {
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