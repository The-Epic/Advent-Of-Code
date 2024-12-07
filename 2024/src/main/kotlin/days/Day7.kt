package days

class Day7 : Day(7) {
    override fun partOne(inputLines: List<String>): Any {
        var totalOutput = 0L

        for (line in inputLines) {
            val numbers = line.replace(":", "").split(" ").map { it.toLong() }
            val total = numbers[0]
            val calcNumbers = numbers.subList(2, numbers.size)

            val results = mutableSetOf<Long>()
            results.add(numbers[1])

            for (number in calcNumbers) {
                val newResults = mutableSetOf<Long>()
                for (result in results) {
                    newResults.add(result + number)
                    newResults.add(result * number)
                }
                results.clear()
                results.addAll(newResults)
            }

            if (results.contains(total)) totalOutput += total
        }

        return totalOutput
    }

    override fun partTwo(inputLines: List<String>): Any {
        var totalOutput = 0L

        for (line in inputLines) {
            val numbers = line.replace(":", "").split(" ").map { it.toLong() }
            val total = numbers[0]
            val calcNumbers = numbers.subList(2, numbers.size)

            val results = mutableSetOf<Long>()
            results.add(numbers[1])

            for (number in calcNumbers) {
                val newResults = mutableSetOf<Long>()
                for (result in results) {
                    newResults.add(result + number)
                    newResults.add(result * number)
                    newResults.add("$result$number".toLong())
                }
                results.clear()
                results.addAll(newResults)
            }

            if (results.contains(total)) totalOutput += total
        }

        return totalOutput
    }
}
