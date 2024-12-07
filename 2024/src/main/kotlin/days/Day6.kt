package days

class Day6 : Day(6) {


    override fun partOne(inputLines: List<String>): Any {

        var startLocation: Pair<Int, Int> = -1 to -1

        for ((xIndex, x) in inputLines.withIndex()) {
            for ((zIndex, z) in x.withIndex()) {
                if (z == '^') {
                    startLocation = xIndex to zIndex
                    break
                }
            }

            if (startLocation.first != -1 && startLocation.second != -1) break
        }

        // set start direction, default north, while true move in direction by adding rel cords, if coord x is small than 0 its finished, if its bigger than input its done
        // if x is smaller than 0 its finished, if its bigger than line.length its done
        // inside loop create a counter of spaces moved, upon next location being a '#' set direction to direc.next and continue loop

        val walkedSpaces = mutableListOf<Pair<Int, Int>>()
        var movingDirection = Direction.NORTH

        var dirX = startLocation.first
        var dirZ = startLocation.second
        while (true) {
            if (dirX < 0) break
            if (dirX >= inputLines.size) break
            if (dirZ < 0) break
            if (dirZ >= inputLines[dirX].length) break
            var relativeDir = movingDirection.relativeCoords


            val currentChar = charAt(inputLines, dirX, dirZ)
            val nextChar = charAt(inputLines, dirX + relativeDir.first, dirZ + relativeDir.second)

            if (currentChar == '.' || currentChar == '^') {
                walkedSpaces.add(dirX to dirZ)
            }

            if (nextChar == ' ') {
                break
            } else if (nextChar == '#') {
                movingDirection = movingDirection.next
                relativeDir = movingDirection.relativeCoords
            }

            dirX += relativeDir.first
            dirZ += relativeDir.second
        }

        val spacesDistinct = walkedSpaces.distinct()

        return spacesDistinct.size
    }

    private fun charAt(lines: List<String>, x: Int, z: Int): Char = try {
        lines[x][z]
    } catch (_: Exception) {
        ' '
    }

    override fun partTwo(inputLines: List<String>): Any {
        return "Unable to Solve."
    }

}


private enum class Direction(val relativeCoords: Pair<Int, Int>) {
    NORTH(-1 to 0),
    EAST(0 to 1),
    SOUTH(1 to 0),
    WEST(0 to -1);

    lateinit var next: Direction

    companion object {
        init {
            // Define the circular dependencies here
            NORTH.next = EAST
            EAST.next = SOUTH
            SOUTH.next = WEST
            WEST.next = NORTH
        }
    }
}