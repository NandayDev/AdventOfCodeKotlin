package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_04_test")
    val testResult = part1(testInput)
    println("Test 1 result: $testResult")
    check(testResult == 18)

    val input = readInput("2024_04")
    part1(input).println()

    val testResult2 = part2(testInput)
    println("Test 2 result: $testResult2")
    check(testResult2 == 9)

    part2(input).println()
}

private fun part1(input: List<String>): Int {
    var xmasFound = 0
    for (i in input.indices) {
        val row = input[i]
        for (k in row.indices) {
            for (iModifier in Modifier.entries) {
                for (kModifier in Modifier.entries) {
                    if (iModifier == Modifier.UNCHANGING && kModifier == Modifier.UNCHANGING) {
                        continue
                    }
                    if (isXmas(input, i, k, iModifier, kModifier)) {
                        xmasFound++
                    }
                }
            }
        }
    }
    return xmasFound
}

private fun isXmas(
    input: List<String>,
    i: Int,
    k: Int,
    iModifier: Modifier,
    kModifier: Modifier
): Boolean {
    if (input[i][k] != 'X') return false
    if (iModifier == Modifier.INCREASING && i > input.size - 4) return false
    if (iModifier == Modifier.DECREASING && i < 3) return false
    if (kModifier == Modifier.INCREASING && k > input[0].length - 4) return false
    if (kModifier == Modifier.DECREASING && k < 3) return false

    val nextXmasLetters = "MAS"
    for (n in nextXmasLetters.indices) {
        val iNext = when (iModifier) {
            Modifier.UNCHANGING -> i
            Modifier.INCREASING -> i + 1 + n
            Modifier.DECREASING -> i - 1 - n
        }
        val kNext = when (kModifier) {
            Modifier.UNCHANGING -> k
            Modifier.INCREASING -> k + 1 + n
            Modifier.DECREASING -> k - 1 - n
        }
        if (input[iNext][kNext] != nextXmasLetters[n]) {
            return false
        }
    }
    return true
}

private fun part2(input: List<String>): Int {
    var xmasFound = 0
    for (i in input.indices) {
        if (i > input.size - 3) {
            continue
        }
        val row = input[i]
        for (k in row.indices) {
            if (k > row.length - 3) {
                continue
            }
            if (input[i + 1][k + 1] == 'A') {
                if ((input[i][k] == 'M' && input[i + 2][k + 2] == 'S')
                    || (input[i][k] == 'S' && input[i + 2][k + 2] == 'M')
                ) {
                    if ((input[i][k + 2] == 'M' && input[i + 2][k] == 'S')
                        || (input[i][k + 2] == 'S' && input[i + 2][k] == 'M')
                    ) {
                        xmasFound++
                    }
                }
            }
        }
    }
    return xmasFound
}

enum class Modifier {
    UNCHANGING,
    INCREASING,
    DECREASING
}