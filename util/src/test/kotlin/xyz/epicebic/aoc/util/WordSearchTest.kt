package xyz.epicebic.aoc.util

import org.junit.jupiter.api.Test

class WordSearchTest {

    private val INPUT: List<String>
        get() = listOf(
            "S..S..S",
            ".A.A.A.",
            "..MMM..",
            "SAMXMAS",
            "..MMM..",
            ".A.A.A.",
            "S..S..S"
        )

    @Test
    fun test_Search_Finding() {
        val wordSearch = WordSearch(INPUT)
        val word = "XMAS"
        val forward = wordSearch.search(word, Direction.WEST)
        assert(forward == 1)
        val backward = wordSearch.search(word, Direction.EAST)
        assert(backward == 1)
        val up = wordSearch.search(word, Direction.NORTH)
        assert(up == 1)
        val down = wordSearch.search(word, Direction.SOUTH)
        assert(down == 1)
        val leftUp = wordSearch.search(word, Direction.NORTH_WEST)
        assert(leftUp == 1)
        val leftDown = wordSearch.search(word, Direction.SOUTH_WEST)
        assert(leftDown == 1)
        val rightUp = wordSearch.search(word, Direction.NORTH_EAST)
        assert(rightUp == 1)
        val rightDown = wordSearch.search(word, Direction.SOUTH_EAST)
        assert(rightDown == 1)

        val all = wordSearch.search(word)
        assert(all == 8)

    }
}