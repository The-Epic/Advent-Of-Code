
import days.*
import xyz.epicebic.aoc.util.readResourceLines
import kotlin.time.measureTime

fun main(args: Array<String>) {
    val days = listOf(
        Day7(),
        Day6(),
        Day5(),
        Day4(),
        Day3(),
        Day2(),
        Day1()
    )

    if (args.isNotEmpty() && args[0] == "single") {
        val day = days.first()
        runDay(day)
        return
    }

    for (day in days.reversed()) {
        runDay(day)
    }
}

private fun runDay(day: Day) {
    val id = day.number
    val inputLines = readResourceLines("input-$id.txt").dropLastWhile { it.isBlank() }
    println("Day $id")

    var partOneAnswer: Any
    val partOne = measureTime {
        partOneAnswer = day.partOne(inputLines)
    }
    println("Part One: $partOneAnswer (${partOne.inWholeMilliseconds}ms)")

    var partTwoAnswer: Any
    val partTwo = measureTime {
        partTwoAnswer = day.partTwo(inputLines)
    }
    println("Part Two: $partTwoAnswer (${partTwo.inWholeMilliseconds}ms) ")
}
