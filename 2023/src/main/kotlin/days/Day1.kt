package days

class Day1 : Day(1) {
    override fun partOne(inputLines: List<String>): Any {
        return inputLines.map {
            it.toCharArray().filter { it.isDigit() }.map { it.digitToInt() }
        }.sumOf { (it.first().toString() + it.last()).toInt() }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val digits = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )

        return inputLines.sumOf { line ->
            val start = digits.keys.mapNotNull { line.windowed(it.length).indexOf(it).let { index -> if (index == -1) null else digits[it]!! to index } } + digits.values.mapNotNull { line.indexOf(it.toString()).let { index -> if (index == -1) null else it to index } }
            val first = start.minByOrNull { (_, it) -> it }!!.first

            val end = digits.keys.mapNotNull { line.windowed(it.length).lastIndexOf(it).let { index -> if (index == -1) null else digits[it]!! to index } } + digits.values.mapNotNull { line.lastIndexOf(it.toString()).let { index -> if (index == -1) null else it to index } }
            val last = end.maxByOrNull { (_, it) -> it }!!.first

            first * 10 + last
        }
    }

}
