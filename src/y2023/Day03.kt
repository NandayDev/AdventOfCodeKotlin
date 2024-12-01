package y2023

import println
import readInput

fun main() {
    fun Char.isValidSymbol(): Boolean {
        return !isDigit() && this != '.'
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { i, line ->
            var currentNumber = ""
            line.forEachIndexed { k, char ->
                if (char.isDigit()) {
                    currentNumber += char
                }
                if (!char.isDigit() || (char.isDigit() && k == line.length - 1)) {
                    val realK = if (char.isDigit()) k + 1 else k
                    currentNumber.toIntOrNull()?.let { number ->
                        var foundSymbol = false
                        for (l in i - 1 .. i + 1) {
                            if (l < 0 || l > input.size - 1) {
                                continue
                            }
                            for (j in (realK - currentNumber.length - 1)..realK) {
                                if (j < 0 || j > input[l].length - 1) {
                                    continue
                                }
                                foundSymbol = input[l][j].isValidSymbol()
                                if (foundSymbol) {
                                    break
                                }
                            }
                            if (foundSymbol) {
                                break
                            }
                        }
                        if (foundSymbol) {
                            sum += number
                        }
                    }

                    currentNumber = ""
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { i, line ->
            line.forEachIndexed { k, char ->
                if (char == '*') {
                    var firstNumber: FoundNumber? = null
                    var secondNumber: FoundNumber? = null
                    for (l in i - 1 .. i + 1) {
                        if (l < 0 || l > input.size - 1) {
                            continue
                        }
                        for (j in k - 1 .. k + 1) {
                            if (j < 0 || j > line.length - 1) {
                                continue
                            }
                            if (input[l][j].isDigit()) {
                                if (firstNumber == null) {
                                    firstNumber = findNumber(input[l], j)
                                } else if (secondNumber == null) {
                                    val num = findNumber(input[l], j)
                                    if (num != firstNumber) {
                                        secondNumber = num
                                    }
                                }
                            }
                        }
                    }
                    firstNumber?.number?.let { first ->
                        println("Found first $firstNumber")
                        secondNumber?.number?.let { second ->
                            println("Found second $secondNumber")
                            sum += first * second
                        }
                    }
                }
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

private fun findNumber(line: String, position: Int): FoundNumber {
    var currentLeft: Int? = position - 1
    var currentRight: Int? = position + 1
    var numberString = line[position].toString()
    var firstPosition = position
    while (currentLeft != null || currentRight != null) {
        // Backward 1 //
        if (currentLeft != null && currentLeft >= 0) {
            line[currentLeft].toString().toIntOrNull()?.let {
                numberString = "$it$numberString"
                firstPosition = currentLeft!!
                currentLeft = currentLeft!! - 1
            } ?: run {
                currentLeft = null
            }
        } else {
            currentLeft = null
        }
        // Forward 1 //
        if (currentRight != null && currentRight < line.length) {
            line[currentRight].toString().toIntOrNull()?.let {
                numberString = "$numberString$it"
                currentRight = currentRight!! + 1
            } ?: run {
                currentRight = null
            }
        } else {
            currentRight = null
        }
    }
    return FoundNumber(line, firstPosition, numberString.toInt())
}

data class FoundNumber(
    val line: String,
    val position: Int,
    val number: Int
)