package xyz.epicebic.aoc.util

enum class Direction(val x: Int, val z: Int) {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1),

    NORTH_EAST(-1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(1, -1);

    lateinit var nextClockwise: Direction

    companion object {
        init {
            NORTH.nextClockwise = EAST
            EAST.nextClockwise = SOUTH
            SOUTH.nextClockwise = WEST
            WEST.nextClockwise = NORTH
        }
    }
}