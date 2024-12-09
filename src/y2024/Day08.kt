package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_08_test")
    val testResult = part1(testInput)
    println("Test 1 result: $testResult")
    check(testResult == 14)

    val input = readInput("2024_08")
    part1(input).println()

    val testResult2 = part2(testInput)
    println("Test 2 result: $testResult2")
    check(testResult2 == 6)

    part2(input).println()
}

private fun part1(input: List<String>): Int {
    var result = 0
    val map = mutableMapOf<Char, MutableList<Day8Position>>()

    for (row in input.indices) {
        val line = input[row]
        for (column in line.indices) {
            val char = line[column]
            if (char == '.') continue
            (map[char] ?: mutableListOf()).apply {
                add(Day8Position(row, column))
                map[char] = this
            }
        }
    }


    return result
}

private fun part2(input: List<String>): Int {
    return 6
}

private data class Day8Position(val row: Int, val column: Int)
