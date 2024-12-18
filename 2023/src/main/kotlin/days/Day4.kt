package days

class Day4 : Day(4) {

    private data class Card(val winning: List<Int>, val shown: List<Int>)

    override fun partOne(inputLines: List<String>): Any {
        val cards = inputLines.map {
            val numbers = it.split(':').last()
            val (winning, shown) = numbers.split('|').map {
                it.trim().split(" ").mapNotNull{ it.trim().toIntOrNull() }
            }

            Card(winning, shown)
        }

        return cards.sumOf { card ->

            var points = 0
            for (number in card.shown) {
                if (card.winning.contains(number)) {
                    if (points == 0) {
                        points++
                    } else {
                        points *= 2
                    }
                }
            }

            points
        }
    }

    override fun partTwo(inputLines: List<String>): Any {
        // Parse the input into cards
        val cards = inputLines.map {
            val numbers = it.split(':').last()
            val (winning, shown) = numbers.split('|').map {
                it.trim().split(" ").mapNotNull { it.trim().toIntOrNull() }
            }
            Card(winning, shown)
        }

        val cache = mutableMapOf<Int, Int>()

        fun computeTotalCards(cardIndex: Int): Int {
            if (cache.containsKey(cardIndex)) {
                return cache[cardIndex]!!
            }

            val currentCard = cards[cardIndex]
            val matches = currentCard.shown.count { it in currentCard.winning }

            var totalCards = 1

            if (matches > 0) {
                for (nextIndex in (cardIndex + 1) until minOf(cards.size, cardIndex + 1 + matches)) {
                    totalCards += computeTotalCards(nextIndex)
                }
            }

            cache[cardIndex] = totalCards
            return totalCards
        }

        return cards.indices.sumOf { computeTotalCards(it) }
    }
}
