package y2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): String {
        var sum = 0
        for (line in input) {
            var currentList = line.split(" ").map { it.toInt() }.toMutableList()
            val differences = mutableListOf(currentList)
            while (!currentList.all { it == currentList[0] }) {
                differences.add(mutableListOf<Int>().apply {
                    for (i in 1 until currentList.size) {
                        add(currentList[i] - currentList[i - 1])
                    }
                    currentList = this
                })
            }
            val lastList = differences[differences.size - 1]
            lastList.add(lastList[lastList.size - 1])
            for (i in differences.size - 2 downTo 0) {
                val thisList = differences[i]
                val nextList = differences[i + 1]
                differences[i].add(thisList[thisList.size - 1] + nextList[nextList.size - 1])
            }
            sum += differences[0][differences[0].size - 1]
        }
        return sum.toString()
    }

    fun part2(input: List<String>): String {
        var sum = 0
        for (line in input) {
            var currentList = line.split(" ").map { it.toInt() }.toMutableList()
            val differences = mutableListOf(currentList)
            while (!currentList.all { it == currentList[0] }) {
                differences.add(mutableListOf<Int>().apply {
                    for (i in 1 until currentList.size) {
                        add(currentList[i] - currentList[i - 1])
                    }
                    currentList = this
                })
            }
            val lastList = differences[differences.size - 1]
            lastList.add(0, lastList[0])
            for (i in differences.size - 2 downTo 0) {
                val thisList = differences[i]
                val nextList = differences[i + 1]
                differences[i].add(0, thisList[0] - nextList[0])
            }
            sum += differences[0][0]
        }
        return sum.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == "114")
    check(part2(testInput) == "2")

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
