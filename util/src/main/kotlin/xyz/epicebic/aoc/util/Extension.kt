package xyz.epicebic.aoc.util


fun String.halve(): Pair<String, String> {
    val split = this.count() / 2
    return substring(0, split) to substring(split)
}

fun List<String>.grid() = Grid.create(this)

fun List<String>.toIntGrid() = Grid(this.map { line -> line.map { it.digitToInt() }.toMutableList() }.toMutableList())