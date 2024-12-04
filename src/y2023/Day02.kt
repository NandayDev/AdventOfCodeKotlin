package y2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val cubesMap = mutableMapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        var sum = 0
        for (line in input) {
            val subLines = line.split(":")
            val gameId = "Game (?<id>\\d+)".toRegex().findAll(subLines[0])
                .first().groups["id"]!!.value.toInt()
            val parts = subLines[1].split(";")
            var valid = true
            for (part in parts) {
                val elements = part.split(",")
                for (element in elements) {
                    val foundFromRegex =
                        " (?<number>\\d+) (?<color>\\w+)".toRegex().findAll(element)
                    for (found in foundFromRegex) {
                        val number = found.groups["number"]!!.value.toInt()
                        val color = found.groups["color"]!!.value
                        if (cubesMap[color]!! < number) {
                            valid = false
                            break
                        }
                    }
                }

            }
            if (valid) {
                sum += gameId
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val cubesMap = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )
            val subLines = line.split(":")
            val parts = subLines[1].split(";")
            for (part in parts) {
                val elements = part.split(",")
                for (element in elements) {
                    val foundFromRegex =
                        " (?<number>\\d+) (?<color>\\w+)".toRegex().findAll(element)
                    for (found in foundFromRegex) {
                        val number = found.groups["number"]!!.value.toInt()
                        val color = found.groups["color"]!!.value
                        cubesMap[color] = maxOf(cubesMap[color]!!, number)
                    }
                }
            }
            var power = 1
            for (value in cubesMap.values) {
                power *= value
            }
            sum += power
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
