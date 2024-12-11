package xyz.epicebic.aoc.util

fun Any.println() {
    println(this)
}

fun String.halve(): Pair<String, String> {
    val split = this.count() / 2
    return substring(0, split) to substring(split)
}