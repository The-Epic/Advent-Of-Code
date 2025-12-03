package days

import java.math.BigInteger

class Day3 : Day(3) {
    override fun partOne(inputLines: List<String>): Any {
        return inputLines.sumOf { maxDigit(it, 2) }
    }

    fun maxDigit(bank: String, limit: Int): BigInteger {
        val stack = CharArray(limit)
        var top = 0
        val n = bank.length

        for (i in bank.indices) {
            val c = bank[i]
            while (top > 0 && stack[top - 1] < c && top + (n - i) > limit) {
                top--
            }
            if (top < limit) stack[top++] = c
        }

        return BigInteger(String(stack))
    }

    override fun partTwo(inputLines: List<String>): Any {
        return inputLines.sumOf { maxDigit(it, 12) }
    }
}
