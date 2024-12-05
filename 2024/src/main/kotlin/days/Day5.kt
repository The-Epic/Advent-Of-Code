package days

class Day5 : Day(5) {

    private val rules = mutableMapOf<Int, MutableList<Int>>()
    private val updates = mutableListOf<List<Int>>()

    private val invalidUpdates = mutableListOf<MutableList<Int>>()

    private fun setup(lines: List<String>) {
        if (rules.isNotEmpty() && updates.isNotEmpty()) return
        for (line in lines) {
            if (line.contains("|")) {
                val split = line.split("|")
                val left = split[0].toInt()
                val right = split[1].toInt()

                rules.compute(left) { _, rule ->
                    var tempRule = rule
                    if (tempRule == null) {
                        tempRule = mutableListOf()
                    }

                    tempRule.add(right)
                    return@compute tempRule
                }
            } else if (line.contains(",")) {
                val split = line.split(",").map { it.toInt() }
                updates.add(split)
            }
        }
    }

    override fun partOne(inputLines: List<String>): Any {
        setup(inputLines)

        val validUpdates = mutableListOf<List<Int>>()
        for (update in updates) {
            var valid = true

            val printedPage = mutableListOf<Int>()

            for (pageId in update) {
                val rules = rules[pageId]
                if (rules != null && printedPage.any { page -> rules.contains(page) }) {
                    valid = false
                }

                printedPage.add(pageId)
            }

            if (valid) {
                validUpdates.add(update)
            } else {
                invalidUpdates.add(update.toMutableList())
            }
        }
        return validUpdates.sumOf { it[(it.size - 1) / 2] }
    }

    override fun partTwo(inputLines: List<String>): Any {
        for (update in invalidUpdates) {
            var index = update.size - 1
            while (index >= 0) {
                val page = update[index]

                val pageRules = rules[page]
                if (pageRules != null) {
                    for (target in pageRules) {
                        val targetLocation = update.indexOf(target)
                        if (targetLocation != -1 && index > targetLocation) {
                            val currentTarget = update[targetLocation]
                            update[targetLocation] = page
                            update[index] = currentTarget
                            index++
                            break
                        }
                    }
                }

                index--
            }
        }

        return invalidUpdates.sumOf { it[(it.size - 1) / 2 ] }
    }
}
