package y2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            var firstDigit = 'a'
            var secondDigit = 'a'
            for (c in line) {
                if (c.isDigit()) {
                    firstDigit = c
                    break
                }
            }
            for (c in line.reversed()) {
                if (c.isDigit()) {
                    secondDigit = c
                    break
                }
            }

            sum += "$firstDigit$secondDigit".toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val numbers = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
        for (line in input) {
            var firstNumber: Int? = null
            var secondNumber: Int? = null
            var s = ""
            for (c in line) {
                if (c.isDigit()) {
                    if (firstNumber == null) {
                        firstNumber = c.digitToInt()
                    } else {
                        secondNumber = c.digitToInt()
                    }
                    s = ""
                }
                s += c
                if (numbers.containsKey(s)) {
                    if (firstNumber == null) {
                        firstNumber = numbers[s]!!
                    } else {
                        secondNumber = numbers[s]!!
                    }
                    break
                }
            }

            sum += "$firstNumber$secondNumber".toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
