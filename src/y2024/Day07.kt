package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_07_test")
    val testResult = part1(testInput)
    println("Test 1 result: $testResult")
    check(testResult == 3749L)

    val input = readInput("2024_07")
    part1(input).println()

    val testResult2 = part2(testInput)
    println("Test 2 result: $testResult2")
    check(testResult2 == 11387L)

    part2(input).println()
}

private fun part1(input: List<String>): Long {
    var finalResult = 0L
    val operations = Operation.entries
    for (line in input) {
        val parts = line.split(":")
        val result = parts[0].toLong()
        val operands = parts[1].split(" ").mapNotNull { it.toLongOrNull() }

        val operationCombinations = combinations(operations, operands.size - 1)

        for (ops in operationCombinations) {
            val operationResult = applyOperations(operands, ops)
            if (operationResult == result) {
                finalResult += result
                break
            }
        }
    }

    return finalResult
}

private fun <T> combinations(operations: List<T>, length: Int): List<List<T>> {
    if (length == 0) return listOf(emptyList())
    val smallerCombos = combinations(operations, length - 1)
    return smallerCombos.flatMap { combo ->
        operations.map { op -> combo + op }
    }
}

private fun applyOperations(numbers: List<Long>, operations: List<Operation>): Long {
    var result = numbers[0]
    for (i in operations.indices) {
        when (operations[i]) {
            Operation.SUM -> result += numbers[i + 1]
            Operation.TIMES -> result *= numbers[i + 1]
        }
    }
    return result
}


private fun part2(input: List<String>): Long {
    var finalResult = 0L
    val operations = OperationSecondPart.entries
    for (line in input) {
        val parts = line.split(":")
        val result = parts[0].toLong()
        val operands = parts[1].split(" ").mapNotNull { it.toLongOrNull() }

        val operationCombinations = combinations(operations, operands.size - 1)

        for (ops in operationCombinations) {
            val operationResult = applyOperationsSecondPart(operands, ops)
            if (operationResult == result) {
                finalResult += result
                break
            }
        }
    }

    return finalResult
}

private fun applyOperationsSecondPart(numbers: List<Long>, operations: List<OperationSecondPart>): Long {
    var result = numbers[0]
    for (i in operations.indices) {
        when (operations[i]) {
            OperationSecondPart.SUM -> result += numbers[i + 1]
            OperationSecondPart.TIMES -> result *= numbers[i + 1]
            OperationSecondPart.CONCATENATION -> result = (result.toString() + numbers[i + 1]).toLong()
        }
    }
    return result
}

enum class Operation {
    SUM,
    TIMES
}

enum class OperationSecondPart {
    SUM,
    TIMES,
    CONCATENATION
}