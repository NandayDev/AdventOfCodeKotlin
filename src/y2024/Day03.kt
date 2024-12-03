package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_03_test")
    check(part1(testInput[0]) == 161)
    val testInput2 = readInput("2024_03_test_2")
    (part2(testInput2[0]) == 48)

    val input = readInput("2024_03")
    part1(input.joinToString("")).println()
    part2(input.joinToString("")).println()
}

private fun part1(input: String): Int {
    var currentStep = Step.NEW
    var firstNumber = ""
    var secondNumber = ""
    var result = 0
    for (char in input) {
        when (currentStep) {
            Step.NEW -> if (char == 'm') currentStep = Step.MUL_M
            Step.MUL_M -> currentStep = if (char == 'u') Step.MUL_U else Step.NEW
            Step.MUL_U -> currentStep = if (char == 'l') Step.MUL_L else Step.NEW
            Step.MUL_L -> currentStep = if (char == '(') Step.FIRST_PARENTHESIS else Step.NEW
            Step.FIRST_PARENTHESIS -> {
                currentStep = if (char.isDigit()) {
                    firstNumber = "$char"
                    Step.FIRST_NUMBER
                } else {
                    Step.NEW
                }
            }

            Step.FIRST_NUMBER -> {
                when {
                    char.isDigit() -> firstNumber += char
                    char == ',' -> currentStep = Step.COMMA
                    else -> currentStep = Step.NEW
                }
            }

            Step.COMMA -> {
                currentStep = if (char.isDigit()) {
                    secondNumber = "$char"
                    Step.SECOND_NUMBER
                } else {
                    Step.NEW
                }
            }

            Step.SECOND_NUMBER -> when {
                char.isDigit() -> secondNumber += char
                char == ')' -> {
                    result += firstNumber.toInt() * secondNumber.toInt()
                    currentStep = Step.NEW
                }

                else -> currentStep = Step.NEW
            }
        }
    }

    return result
}

private fun part2(input: String): Int {
    var currentStep = Step.NEW
    var firstNumber = ""
    var secondNumber = ""
    var result = 0
    var doOrDont = DoOrDont.DO
    for (i in input.indices) {
        val char = input[i]
        when (char) {
            'o' -> if (i > 0 && input[i - 1] == 'd') doOrDont = DoOrDont.DO
            't' -> {
                if (i > 3 && input[i - 1] == '\'' && input[i - 2] == 'n' && input[i - 3] == 'o' && input[i - 4] == 'd') {
                    doOrDont = DoOrDont.DONT
                }
            }
        }
        when (currentStep) {
            Step.NEW -> if (char == 'm') currentStep = Step.MUL_M
            Step.MUL_M -> currentStep = if (char == 'u') Step.MUL_U else Step.NEW
            Step.MUL_U -> currentStep = if (char == 'l') Step.MUL_L else Step.NEW
            Step.MUL_L -> currentStep = if (char == '(') Step.FIRST_PARENTHESIS else Step.NEW
            Step.FIRST_PARENTHESIS -> {
                currentStep = if (char.isDigit()) {
                    firstNumber = "$char"
                    Step.FIRST_NUMBER
                } else {
                    Step.NEW
                }
            }

            Step.FIRST_NUMBER -> {
                when {
                    char.isDigit() -> firstNumber += char
                    char == ',' -> currentStep = Step.COMMA
                    else -> currentStep = Step.NEW
                }
            }

            Step.COMMA -> {
                currentStep = if (char.isDigit()) {
                    secondNumber = "$char"
                    Step.SECOND_NUMBER
                } else {
                    Step.NEW
                }
            }

            Step.SECOND_NUMBER -> when {
                char.isDigit() -> secondNumber += char
                char == ')' -> {
                    if (doOrDont == DoOrDont.DO) {
                        result += firstNumber.toInt() * secondNumber.toInt()
                    }
                    currentStep = Step.NEW
                }

                else -> currentStep = Step.NEW
            }
        }
    }

    return result
}

enum class Step {
    NEW,
    MUL_M,
    MUL_U,
    MUL_L,
    FIRST_PARENTHESIS,
    FIRST_NUMBER,
    COMMA,
    SECOND_NUMBER
}

enum class DoOrDont {
    DO,
    DONT
}