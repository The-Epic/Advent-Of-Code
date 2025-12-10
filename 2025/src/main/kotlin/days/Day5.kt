package days

class Day5 : Day(5) {
    override fun partOne(inputLines: List<String>): Any {
        val splitIndex = inputLines.indexOf("")

        val freshRanges = inputLines.subList(0, splitIndex)
        val testIds = inputLines.subList(splitIndex + 1, inputLines.size).map { it.toLong() }


        val validIds = freshRanges.map {
            val split = it.split("-")
            split[0].toLong()..split[1].toLong()
        }

        return testIds.sumOf {
            for (nums in validIds) {
                if (nums.contains(it)) {
                    return@sumOf 1.toLong()
                }
            }
            return@sumOf 0
        }
    }

    override fun partTwo(inputLines: List<String>): Any {
        val splitIndex = inputLines.indexOf("")

        val freshRanges = inputLines.subList(0, splitIndex)
        val validIds = freshRanges.map {
            val split = it.split("-")
            split[0].toLong()..split[1].toLong()
        }

        val merged = mergeRanges(validIds)

        return merged.sumOf { it.last - it.first + 1 }
    }

    private fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
        val sorted = ranges.sortedBy { it.first }

        val merged = mutableListOf<LongRange>()
        var currentRange = sorted.first()

        for (next in sorted.drop(1)) {
            if (next.first <= currentRange.last + 1) {
                currentRange = currentRange.first..maxOf(currentRange.last, next.last)
            } else {
                merged += currentRange
                currentRange = next
            }
        }

        merged += currentRange
        return merged
    }
}
