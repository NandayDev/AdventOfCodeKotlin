package y2023

import println
import readInput
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): String {
        for (line in input) {
            val (springSet, groups) = line.split(" ").let {
                it[0] to it[1].split(",").map { n -> n.toInt() }
            }
            val nonEmpty = springSet.split(".")
            groups.forEachIndexed { i, group ->

            }
        }
        return ""
    }

    fun part2(input: List<String>): String {
        val result = 2 * (10.0.pow(31.0))
        Int.MAX_VALUE
        return ""
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == "21")
    check(part2(testInput) == "")

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}