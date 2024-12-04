
import days.*
import xyz.epicebic.aoc.util.readResourceLines

fun main() {
    val days = listOf(
        Day4(),
        Day3(),
        Day2(),
        Day1()
    )

    for (day in days.reversed()) {
        val id = day.number
        val inputLines = readResourceLines("input-$id.txt")
        println("Day $id")
        println("Part One: ${day.partOne(inputLines)}")
        println("Part Two: ${day.partTwo(inputLines)}")
    }
}
