package days

class Day2 : Day(2) {
    override fun partOne(inputLines: List<String>): Any {
        val input = inputLines.first()

        val products = input.split(",")
            .map { it.split("-") }
            .flatMap { (f, l) -> f.toLong()..l.toLong() }

        val matching = products.filter { Regex("^(.+)\\1$").matches(it.toString()) }

        return matching.sum()
    }

    override fun partTwo(inputLines: List<String>): Any {
        val input = inputLines.first()

        val products = input.split(",")
            .map { it.split("-") }
            .flatMap { (f, l) -> f.toLong()..l.toLong() }

        val matching = products.filter { Regex("^(.+)\\1+$").matches(it.toString()) }

        return matching.sum()
    }
}
