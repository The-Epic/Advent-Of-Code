package days

abstract class Day(val number: Int) {

    abstract fun partOne(inputLines: List<String>): Any

    abstract fun partTwo(inputLines: List<String>): Any
}