package days

// file|free|file|free|file

class Day9 : Day(9) {
    override fun partOne(inputLines: List<String>): Any {
        val inputDigits = inputLines.first().map { it.digitToInt() }
        val converted = mutableListOf<Int>()

        var fileIndex = 0
        for ((index, char) in inputDigits.withIndex()) {
            repeat(char) {
                converted.add(if (index % 2 == 0) fileIndex else -1)
            }
            if (index % 2 == 0) fileIndex++
        }

        var first = 0
        var second = converted.size - 1

        while (-1 in converted) {
            if (converted[first] != -1) {
                first++
                continue
            }

            converted[first] = converted[second]
            converted.removeAt(second)
            second--
        }

        return converted.withIndex().sumOf { (index, digit) -> index.toLong() * digit }
    }

    private data class Space(val pos: Int, val size: Int)
    private data class File(val pos: Int, val size: Int, val id: Int)

    override fun partTwo(inputLines: List<String>): Any {
        val fileQueue = ArrayDeque<File>()
        val spaceQueue = ArrayDeque<Space>()
        val result = mutableListOf<Int?>()

        var pos = 0
        var fileId = 0
        inputLines.first().map { it.digitToInt() }.withIndex().forEach { (index, char) ->
            if (index % 2 == 0) {
                fileQueue.add(File(pos, char, fileId))
                repeat((pos..<pos + char).count()) {
                    result.add(fileId)
                }
                pos += char
                fileId += 1
            } else {
                spaceQueue.add(Space(pos, char))
                result.addAll(List(char) { null })
                pos += char
            }
        }

        fileQueue.reversed().forEach { (pos, size, fileId) ->
            spaceQueue.withIndex().firstOrNull { (_, space) ->
                space.pos < pos && size <= space.size
            }?.let { (si, s) ->
                (0..< size).forEach { index ->
                    result[pos + index] = null
                    result[s.pos + index] = fileId
                }
                spaceQueue[si] = Space(s.pos + size, s.size - size)
            }
        }

        return result.withIndex().sumOf { (index, value) -> index.toLong() * (value ?: 0) }
    }
}
