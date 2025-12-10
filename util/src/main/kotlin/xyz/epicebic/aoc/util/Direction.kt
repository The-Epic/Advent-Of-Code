package xyz.epicebic.aoc.util

enum class Direction(val x: Int, val y: Int) {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1),

    NORTH_EAST(-1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(1, -1);

    lateinit var nextClockwise: Direction
    lateinit var nextAntiClockwise: Direction

    companion object {
        init {
            NORTH.nextClockwise = EAST
            EAST.nextClockwise = SOUTH
            SOUTH.nextClockwise = WEST
            WEST.nextClockwise = NORTH

            NORTH.nextAntiClockwise = WEST
            EAST.nextAntiClockwise = NORTH
            SOUTH.nextAntiClockwise = EAST
            WEST.nextAntiClockwise = SOUTH
        }

        val CARDINAL_DIRECTIONS = listOf(NORTH, EAST, SOUTH, WEST)

        fun directionFrom(character: Char): Direction = when(character) {
            '^' -> NORTH
            'v' -> SOUTH
            '>' -> EAST
            '<' -> WEST
            else -> error("Invalid direction character '$character")
        }
    }
}