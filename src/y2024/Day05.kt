package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_05_test")
    val testResult = part1(testInput)
    println("Test 1 result: $testResult")
    check(testResult == 143)

    val input = readInput("2024_05")
    part1(input).println()

    val testResult2 = part2(testInput)
    println("Test 2 result: $testResult2")
    check(testResult2 == 123)

    part2(input).println()
}

private fun part1(input: List<String>): Int {
    var result = 0
    var readingFirstSection = true
    val orderingRulesMap = mutableMapOf<Int, MutableSet<Int>>()
    for (line in input) {
        if (line.isEmpty()) {
            readingFirstSection = false
            continue
        }
        if (readingFirstSection) {
            val split = line.split('|')
            val key = split[0].toInt()
            if (!orderingRulesMap.containsKey(key)) {
                orderingRulesMap[key] = mutableSetOf()
            }
            orderingRulesMap[split[0].toInt()]!!.add(split[1].toInt())
        } else {
            var validLine = true
            val numbers = line.split(",").map { it.toInt() }
            for (i in numbers.size - 1 downTo 1) {
                val number = numbers[i]
                val previous = numbers[i - 1]
                if (!orderingRulesMap.containsKey(previous)) {
                    validLine = false
                    break
                }
                val set = orderingRulesMap[previous]!!
                if (!set.contains(number)) {
                    validLine = false
                    break
                }
            }
            if (validLine) {
                result += numbers[numbers.size / 2]
            }
        }
    }

    return result
}

private fun part2(input: List<String>): Int {
    var result = 0
    var readingFirstSection = true
    val orderingRulesMap = mutableMapOf<Int, MutableSet<Int>>()
    for (line in input) {
        if (line.isEmpty()) {
            readingFirstSection = false
            continue
        }
        if (readingFirstSection) {
            val split = line.split('|')
            val key = split[0].toInt()
            if (!orderingRulesMap.containsKey(key)) {
                orderingRulesMap[key] = mutableSetOf()
            }
            orderingRulesMap[split[0].toInt()]!!.add(split[1].toInt())
        } else {
            var validLine = true
            val numbers = line.split(",").map { it.toInt() }
            val copy = numbers.toMutableList()
            while (true) {
                var foundErrors = false
                for (i in numbers.size - 1 downTo 1) {
                    val number = copy[i]
                    val previous = copy[i - 1]
                    if (!orderingRulesMap.containsKey(previous)) {
                        copy[i] = previous
                        copy[i - 1] = number
                        validLine = false
                        foundErrors = true
                        continue
                    }
                    val set = orderingRulesMap[previous]!!
                    if (!set.contains(number)) {
                        copy[i] = previous
                        copy[i - 1] = number
                        validLine = false
                        foundErrors = true
                        continue
                    }
                }
                if (!foundErrors) {
                    break
                }
            }

            if (!validLine) {
                result += copy[copy.size / 2]
            }
        }
    }

    return result
}