package y2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val times = "\\d+".toRegex().findAll(input[0]).map { it.value.toInt() }.toList()
        val distances = "\\d+".toRegex().findAll(input[1]).map { it.value.toInt() }.toList()
        var total = 1

        for (i in times.indices) {
            val time = times[i]
            val recordDistance = distances[i]
            var beatRecordCount = 0
            for (timeHoldingButton in 1 until time) {
                val distanceTravelled = timeHoldingButton * (time - timeHoldingButton)
                if (distanceTravelled > recordDistance) {
                    beatRecordCount++
                }
            }
            total *= beatRecordCount
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val time =
            "\\d+".toRegex().findAll(input[0]).joinToString(separator = "") { it.value }.toLong()
        val recordDistance =
            "\\d+".toRegex().findAll(input[1]).joinToString(separator = "") { it.value }.toLong()
        var beatRecordCount = 0
        for (timeHoldingButton in 1..time) {
            val distanceTravelled = timeHoldingButton * (time - timeHoldingButton)
            if (distanceTravelled > recordDistance) {
                beatRecordCount++
            }
        }


        return beatRecordCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
