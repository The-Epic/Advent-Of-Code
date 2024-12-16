package xyz.epicebic.aoc.util

class Grid<T : Comparable<T>>(val rows: Int, val cols: Int, var currentRow: Int, var currentCol: Int, private val grid: MutableList<MutableList<T>>) {

    constructor(rows: Int, cols: Int, grid: MutableList<MutableList<T>>): this(rows, cols, 0, 0, grid)

    constructor(grid: MutableList<MutableList<T>>) : this(grid.count(), grid[0].count(), grid)

    val currentPos get() = GridPoint(currentRow, currentCol)

    fun move(row: Int, col: Int): Grid<T> {
        if (row < 0 || row >= rows || col < 0 || col >= cols) error("Invalid access of grid at $row, $col")

        currentRow = row
        currentCol = col

        return this
    }

    fun move(point: GridPoint): Grid<T> {
        if (point.x < 0 || point.x >= rows || point.z < 0 || point.z >= cols) error("Invalid access of grid at ${point.x}, ${point.z}")

        currentRow = point.x
        currentCol = point.z

        return this
    }

    fun look(row: Int, col: Int): T? {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return null

        return grid[row][col]
    }

    fun look(point: GridPoint): T? {
        if (point.x < 0 || point.x >= rows || point.z < 0 || point.z >= cols) return null

        return grid[point.x][point.z]
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
        currentCol = location.z
        return this
    }

    fun set(value: T): Grid<T> {
        grid[currentRow][currentCol] = value

        return this
    }

    fun set(point: GridPoint, value: T): Grid<T> {
        if (point.x < 0 || point.x >= rows || point.z < 0 || point.z >= cols) error("Invalid modification of grid at ${point.x}, ${point.z}")

        grid[point.x][point.z] = value

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
        fun create(input: List<String>): Grid<Char> {
            val grid = input.map { it.toCharArray().toMutableList() }
            if (!grid.map { it.size }.all { size -> grid[0].size == size }) {
                error("Grid has mis-matched column amounts")
            }

            return Grid(grid.size, grid[0].size, grid.toMutableList())
        }
    }
}

data class GridPoint(val x: Int, val z: Int) : Comparable<GridPoint> {
    override fun compareTo(other: GridPoint) = compareValuesBy(this, other, GridPoint::x, GridPoint::z)

    operator fun plus(other: Direction) = GridPoint(x + other.x, z + other.z)
    operator fun plus(other: GridPoint) = GridPoint(x + other.x, z + other.z)
}