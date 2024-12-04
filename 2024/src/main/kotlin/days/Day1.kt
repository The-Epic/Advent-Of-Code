package days

import kotlin.math.max
import kotlin.math.min

class Day1 : Day(1) {
    override fun partOne(inputLines: List<String>): Any {

        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        for (line in inputLines) {
            val split = line.split("   ")
            leftList.add(split[0].toInt())
            rightList.add(split[1].toInt())
        }

        leftList.sort()
        rightList.sort()

        var finalNumber = 0
        for (combined in leftList.zip(rightList)) {
            val a = combined.first
            val b = combined.second

            val answer = max(a, b) - min(a, b)
            finalNumber += answer
        }

        return finalNumber
    }

    override fun partTwo(inputLines: List<String>): Any {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        for (line in inputLines) {
            val split = line.split("   ")
            leftList.add(split[0].toInt())
            rightList.add(split[1].toInt())
        }

        var finalNumber = 0
        for (number in leftList) {
            val count = rightList.count { it == number}
            val toAdd = number * count

            finalNumber += toAdd
        }

        return finalNumber
    }
}