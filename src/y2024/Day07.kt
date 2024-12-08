package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_05_test")
    val testResult = part1(testInput)
    println("Test 1 result: $testResult")
    check(testResult == 3749)

    val input = readInput("2024_05")
    part1(input).println()

    val testResult2 = part2(testInput)
    println("Test 2 result: $testResult2")
    check(testResult2 == 123)

    part2(input).println()
}

private fun part1(input: List<String>): Int {
    for (line in input) {
        val parts = line.split(":")
        val result = parts[0].toInt()
        val operands = parts[1].split(" ").mapNotNull { it.toIntOrNull() }

        for (operand in operands) {
            for (operand in operands) {

            }
        }

    }


    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

enum class Operation {
    PLUS,
    TIMES
}