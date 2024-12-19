package days

class Day19 : Day(19) {
    override fun partOne(inputLines: List<String>): Any {
        val (towels, designs) = parse(inputLines)

        return designs.count {
            run(it, towels) >= 1
        }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val (towels, designs) = parse(inputLines)

        return designs.sumOf { run(it, towels) }
    }

    private val cache = HashMap<String, Long>()
    private fun run(design: String, towels: List<String>): Long {
        if (design.isEmpty()) return 1L
        cache[design]?.let { return it }

        return towels.sumOf { if (design.startsWith(it)) run(design.drop(it.length), towels) else 0 }.also { cache[design] = it }
    }

    private fun parse(input: List<String>) = input.let { it.first().split(", ") to it.drop(2) }
}
