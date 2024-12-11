package days

class Day11 : Day(11) {
    override fun partOne(inputLines: List<String>): Any {
        return inputLines.first().split(" ").sumOf { stone -> count(25, 0, stone.toLong()) }
    }

    override fun partTwo(inputLines: List<String>): Any {
        cache.clear()
        return inputLines.first().split(" ").sumOf { stone -> count(75, 0, stone.toLong()) }
    }

    private val cache = HashMap<Pair<Int, Long>, Long>()
    private fun count(limit: Int, rounds: Int, stone: Long): Long {
        if (rounds == limit) return 1
        cache[rounds to stone]?.let { return it }
        cache[rounds to stone] = if (stone == 0L) {
            count(limit, rounds + 1, 1)
        } else if (stone.toString().length % 2 == 0) {
            val string = stone.toString()
            val a = string.substring(0, string.count() / 2)
            val b = string.substring(string.count() / 2)
            count(limit, rounds + 1, a.toLong()) + count(limit, rounds + 1, b.toLong())
        } else count(limit, rounds + 1, stone * 2024)
        return cache[rounds to stone]!!
    }
    
    private fun trimZeros(string: String): String {
        var modified = string
        while (modified.startsWith("0") && modified.count() > 1) {
            modified = modified.removePrefix("0")
        }

        return modified
    }
}
