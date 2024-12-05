
import days.*
import xyz.epicebic.aoc.util.readResourceLines

fun main(args: Array<String>) {
    val days = listOf(
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
    val inputLines = readResourceLines("input-$id.txt")
    println("Day $id")
    println("Part One: ${day.partOne(inputLines)}")
    println("Part Two: ${day.partTwo(inputLines)}")
}
