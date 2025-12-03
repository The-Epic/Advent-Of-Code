package days

class Day1 : Day(1) {
    override fun partOne(inputLines: List<String>): Any {
        var location = 50

        fun move(direction: Char, steps: Int) {
            when (direction) {
                'L' -> location -= steps
                'R' -> location += steps
            }

            location = ((location % 100) + 100) % 100
        }

        var zeroCount = 0

        inputLines.forEach {
            val dir = it[0]
            val steps = it.substring(1).toInt()
            move(dir, steps)

            if (location == 0) {
                zeroCount++
            }
        }


        return zeroCount
    }

    override fun partTwo(inputLines: List<String>): Any {
        var location = 50
        var zeroCount = 0

        inputLines.forEach {
            val dir = it[0]
            val steps = it.substring(1).toInt()

            repeat(steps) {
                location = if (dir == 'R') location + 1 else location - 1
                location = (location + 100) % 100

                if (location == 0) zeroCount++
            }
        }

        return zeroCount
    }
}
