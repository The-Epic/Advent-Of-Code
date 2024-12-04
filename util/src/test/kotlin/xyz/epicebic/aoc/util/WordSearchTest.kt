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
        val forward = wordSearch.search(word, WordSearch.Direction.FORWARD)
        assert(forward == 1)
        val backward = wordSearch.search(word, WordSearch.Direction.BACKWARD)
        assert(backward == 1)
        val up = wordSearch.search(word, WordSearch.Direction.UP)
        assert(up == 1)
        val down = wordSearch.search(word, WordSearch.Direction.DOWN)
        assert(down == 1)
        val leftUp = wordSearch.search(word, WordSearch.Direction.DIAGONAL_LEFT_UP)
        assert(leftUp == 1)
        val leftDown = wordSearch.search(word, WordSearch.Direction.DIAGONAL_LEFT_DOWN)
        assert(leftDown == 1)
        val rightUp = wordSearch.search(word, WordSearch.Direction.DIAGONAL_RIGHT_UP)
        assert(rightUp == 1)
        val rightDown = wordSearch.search(word, WordSearch.Direction.DIAGONAL_RIGHT_DOWN)
        assert(rightDown == 1)

        val all = wordSearch.search(word)
        assert(all == 8)

    }
}