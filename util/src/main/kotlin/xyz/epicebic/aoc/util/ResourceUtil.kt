package xyz.epicebic.aoc.util

fun readResourceLines(name: String): MutableList<String> {
    return object {}.javaClass.classLoader.getResourceAsStream(name)!!.reader().readLines().toMutableList()
}