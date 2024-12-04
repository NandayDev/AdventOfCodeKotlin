package y2024

import println
import readInput
import kotlin.math.abs

fun main() {
    val testInput = readInput("2024_02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("2024_02")
    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<String>): Int {
    var safe = 0
    for (line in input) {
        var current = ""
        var previousInt: Int? = null
        var increasing: Boolean? = null
        var safeReport = true
        for (i in 0..line.length) {
            if (i != line.length) {
                val char = line[i]
                if (char.isDigit()) {
                    current += char
                    continue
                }
            }
            val currentInt = current.toInt()
            if (increasing == null && previousInt != null) {
                increasing =
                    if (currentInt > previousInt) true else if (currentInt < previousInt) false else {
                        safeReport = false
                        break
                    }
            }

            if (previousInt != null) {
                if (previousInt == currentInt || abs(currentInt - previousInt) > 3) {
                    safeReport = false
                    break
                }
            }

            if (increasing != null) {
                if ((increasing && currentInt < previousInt!!) || (!increasing && currentInt > previousInt!!)) {
                    safeReport = false
                    break
                }
            }
            previousInt = currentInt
            current = ""
        }

        if (safeReport) {
            safe++
        }
    }
    return safe
}

private fun part2(input: List<String>): Int {
    val reports = mutableListOf<MutableList<Int>>()
    for (line in input) {
        val list = mutableListOf<Int>()
        reports.add(list)
        var current = ""
        for (i in 0..line.length) {
            if (i != line.length) {
                val char = line[i]
                if (char.isDigit()) {
                    current += char
                    continue
                }
            }
            val currentInt = current.toInt()
            list.add(currentInt)
            current = ""
        }
    }

    var validReportCount = 0

    for (report in reports) {
        if (isReportSafe(report)) {
            validReportCount++
            continue
        }
        var anyReportSafe = false

        for (i in report.indices) {
            val newReport = report.toMutableList().apply { removeAt(i) }
            if (isReportSafe(newReport)) {
                anyReportSafe = true
                break
            }
        }
        if (anyReportSafe) {
            validReportCount++
        }
    }

    return validReportCount
}

private fun isReportSafe(list: MutableList<Int>): Boolean {
    var previous = list[0]
    val increasing = if (list[1] > previous) true else if (list[1] < previous) false else {
        return false
    }
    if (abs(list[1] - previous) > 3) {
        return false
    }
    previous = list[1]
    for (i in 2 until list.size) {
        val current = list[i]
        if (current == previous || abs(current - previous) > 3) {
            return false
        }

        if ((increasing && current < previous) || (!increasing && current > previous)) {
            return false
        }
        previous = current
    }
    return true
}