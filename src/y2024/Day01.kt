package y2024

import println
import readInput
import kotlin.math.abs

fun main() {
    val testInput = readInput("2024_01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("2024_01")
    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<String>): Int {
    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    for (line in input) {
        var s = ""
        for (i in line.indices) {
            val char = line[i]
            if (char.isDigit()) {
                s += char
                if (i == line.length - 1) {
                    rightList.add(s.toInt())
                }
            } else if (s.isNotEmpty()) {
                leftList.add(s.toInt())
                s = ""
            }
        }
    }

    bubbleSortAscending(leftList)
    bubbleSortAscending(rightList)

    var sum = 0

    for (i in leftList.indices) {
        sum += abs(leftList[i] - rightList[i])
    }


    return sum
}

private fun part2(input: List<String>): Int {
    val leftList = mutableListOf<Int>()
    val rightMap = mutableMapOf<Int, Int>()

    for (line in input) {
        var s = ""
        for (i in line.indices) {
            val char = line[i]
            if (char.isDigit()) {
                s += char
                if (i == line.length - 1) {
                    val number = s.toInt()
                    rightMap[number] = (rightMap[number] ?: 0) + 1
                }
            } else if (s.isNotEmpty()) {
                leftList.add(s.toInt())
                s = ""
            }
        }
    }

    var sum = 0

    for (number in leftList) {
        sum += number * (rightMap[number] ?: 0)
    }

    return sum
}

private fun bubbleSortAscending(list: MutableList<Int>) {
    for (i in 0..list.size - 2) {
        for (k in 0..list.size - i - 2) {
            val current = list[k]
            val next = list[k + 1]
            if (current > next) {
                list[k] = next
                list[k + 1] = current
            }
        }
    }
}