package xyz.epicebic.aoc.util

import kotlin.math.abs

class Grid<T : Comparable<T>>(val rows: Int, val cols: Int, var currentRow: Int, var currentCol: Int, private val grid: MutableList<MutableList<T>>) {

    constructor(rows: Int, cols: Int, grid: MutableList<MutableList<T>>): this(rows, cols, 0, 0, grid)

    constructor(grid: MutableList<MutableList<T>>) : this(grid.count(), grid[0].count(), grid)

    val currentPos get() = GridPoint(currentRow, currentCol)

    fun move(row: Int, col: Int): Grid<T> {
        if (row !in 0..<rows || col < 0 || col >= cols) error("Invalid access of grid at $row, $col")

        currentRow = row
        currentCol = col

        return this
    }

    fun move(point: GridPoint): Grid<T> {
        if (point.x !in 0..<rows || point.y < 0 || point.y >= cols) error("Invalid access of grid at ${point.x}, ${point.y}")

        currentRow = point.x
        currentCol = point.y

        return this
    }

    fun look(row: Int, col: Int): T? {
        if (row !in 0..<rows || col < 0 || col >= cols) return null

        return grid[row][col]
    }

    fun look(point: GridPoint): T? {
        if (point.x !in 0..<rows || point.y < 0 || point.y >= cols) return null

        return grid[point.x][point.y]
    }

    fun move(direction: Direction): Grid<T> {
        return move(currentRow + direction.x, currentCol + direction.z)
    }

    fun look(direction: Direction): T? {
        return look(currentRow + direction.x, currentCol + direction.z)
    }

    fun locateFirst(value: T): GridPoint {
        for (row in 0..rows) {
            for (col in 0..cols) {
                if(look(row, col) == value) return GridPoint(row, col)
            }
        }

        error("Unable to locate character '$value'")
    }

    fun moveToFirst(value: T): Grid<T> {
        val location = locateFirst(value)

        currentRow = location.x
        currentCol = location.y
        return this
    }

    fun set(value: T): Grid<T> {
        grid[currentRow][currentCol] = value

        return this
    }

    fun set(point: GridPoint, value: T): Grid<T> {
        if (point.x !in 0..<rows || point.y < 0 || point.y >= cols) error("Invalid modification of grid at ${point.x}, ${point.y}")

        grid[point.x][point.y] = value

        return this
    }

    fun currentChar(): T {
        return grid[currentRow][currentCol]
    }

    fun locateAll(value: T): List<GridPoint> {
        val foundPoints = mutableListOf<GridPoint>()
        for (row in 0 .. rows ) {
            for (col in 0 .. cols) {
                if (look(row, col) == value) foundPoints.add(GridPoint(row, col))
            }
        }

        return foundPoints
    }

    fun getCardinalNeighbourPoints(): List<GridPoint> {
        return Direction.CARDINAL_DIRECTIONS.map { currentPos + it }
    }

    fun getCardinalNeighbourPoints(point: GridPoint): List<GridPoint> {
        return Direction.CARDINAL_DIRECTIONS.map { point + it }
    }



    fun clone(): Grid<T> {
        val copy = ArrayList<ArrayList<T>>(rows)

        for (row in 0 until rows) {
            copy.add(ArrayList(cols))

            for (col in 0 until cols) {
                copy[row].add(grid[row][col])
            }
        }

        return Grid(rows, cols, currentRow, currentCol, copy.toMutableList())
    }

    override fun toString(): String {
        return "Grid(rows=$rows, cols=$cols, currentRow=$currentRow, currentCol=$currentCol, grid=\n${
            grid.joinToString(
                "\n"
            ) { it.joinToString("") }
        }})"
    }


    companion object {

        const val WALL = '#'
        const val OPEN = '.'

        fun create(input: List<String>): Grid<Char> {
            val grid = input.map { it.toCharArray().toMutableList() }
            if (!grid.map { it.size }.all { size -> grid[0].size == size }) {
                error("Grid has mis-matched column amounts")
            }

            return Grid(grid.size, grid[0].size, grid.toMutableList())
        }
    }
}

data class GridPoint(val x: Int, val y: Int) : Comparable<GridPoint> {
    override fun compareTo(other: GridPoint) = compareValuesBy(this, other, GridPoint::x, GridPoint::y)

    operator fun plus(other: Direction) = GridPoint(x + other.x, y + other.z)
    operator fun plus(other: GridPoint) = GridPoint(x + other.x, y + other.y)

    fun manhattanDistance(other: GridPoint) = abs(x - other.x) + abs(y - other.y)
}