package days

class Day13 : Day(13) {
    private data class Point(val x: Long, val y: Long)

    override fun partOne(inputLines: List<String>): Any {
        return solve(inputLines, 0)
    }

    override fun partTwo(inputLines: List<String>): Any {
        return solve(inputLines, 10000000000000)
    }

    private fun solve(input: List<String>, extra: Long): Long {
        val points = extractPoints(input, extra)

        return points.sumOf {
            val (a, b, c) = it
            val (n, m) = magic(a, b, c) ?: return@sumOf 0L

            (n * 3) + m
        }
    }

    private fun extractPoints(inputLines: List<String>, prizeExtra: Long): List<List<Point>> {
        return inputLines.chunked(4).map {
            val out = mutableListOf<Point>()
            for (entry in it) {
                if (entry.isBlank() || entry.isEmpty()) continue
                val numbers = extractSeperated(entry)
                if (out.size == 2) {
                    out.add(Point(numbers[0].toLong() + prizeExtra, numbers[1].toLong()+ prizeExtra))
                } else {
                    out.add(Point(numbers[0].toLong(), numbers[1].toLong()))
                }
            }

            out
        }
    }

    private fun magic(a: Point, b: Point, c: Point): Point? {
        val (gcd, x0, y0) = extendedGcd(a.x, b.x)
        if (c.x % gcd != 0L) return null

        // Find n and m such that n * a + m * b = c
        val x = c.x / gcd
        val nx = x0 * x
        val mx = y0 * x
        val k = (c.y - (nx * a.y + mx * b.y)) / ((b.x * a.y - a.x * b.y) / gcd)
        val n = nx + k * (b.x / gcd)
        val m = mx - k * (a.x / gcd)

        return Point(n, m).takeIf { n * a.x + m * b.x == c.x && n * a.y + m * b.y == c.y }
    }

    private fun extendedGcd(a: Long, b: Long): Triple<Long, Long, Long> {
        if (b == 0L) {
            return Triple(a, 1L, 0L)
        }
        val (gcd, x1, y1) = extendedGcd(b, a % b)
        val y = x1 - (a / b) * y1
        return Triple(gcd, y1, y)
    }

    private fun extractSeperated(line: String) = line.split(Regex("\\D+")).filter { it.isNotBlank() }.map { it.toInt() }
}
