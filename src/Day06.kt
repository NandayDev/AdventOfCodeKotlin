fun main() {
    fun part1(input: List<String>): Int {
        val times = "\\d+".toRegex().findAll(input[0]).map { it.value.toInt() }.toList()
        val distances = "\\d+".toRegex().findAll(input[1]).map { it.value.toInt() }.toList()

        for (i in times.indices) {
            val time = times[i]
            val distance = distances[i]
            for (timeHoldingButton in 1 until time) {

            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 142)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
