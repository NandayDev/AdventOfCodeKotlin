package y2024

import println
import readInput

fun main() {
    val testInput = readInput("2024_06_test")
    val testResult = part1(testInput)
    println("Test 1 result: $testResult")
    check(testResult == 41)

    val input = readInput("2024_06")
    part1(input).println()

    val testResult2 = part2(testInput)
    println("Test 2 result: $testResult2")
    check(testResult2 == 6)

    part2(input).println()
}

private fun part1(input: List<String>): Int {
    var currentPosition: Position? = null
    for (i in input.indices) {
        for (k in input[i].indices) {
            if (input[i][k] == '^') {
                currentPosition = Position(row = i, column = k)
                break
            }
        }
        if (currentPosition != null) {
            break
        }
    }
    var currentDirection = Direction.UP
    val positions = mutableSetOf(currentPosition)
    while (true) {
        currentPosition ?: throw Exception()
        currentPosition = when (currentDirection) {
            Direction.UP -> {
                if (currentPosition.row == 0) {
                    // Bye bye guard //
                    break
                }
                if (input[currentPosition.row - 1][currentPosition.column] == '#') {
                    // Obstacle //
                    currentDirection = currentDirection.next()
                    continue
                }
                Position(currentPosition.row - 1, currentPosition.column)
            }

            Direction.RIGHT -> {
                if (currentPosition.column == input[0].length - 1) {
                    // Bye bye guard //
                    break
                }
                if (input[currentPosition.row][currentPosition.column + 1] == '#') {
                    // Obstacle //
                    currentDirection = currentDirection.next()
                    continue
                }
                Position(currentPosition.row, currentPosition.column + 1)
            }

            Direction.DOWN -> {
                if (currentPosition.row == input.size - 1) {
                    // Bye bye guard //
                    break
                }
                if (input[currentPosition.row + 1][currentPosition.column] == '#') {
                    // Obstacle //
                    currentDirection = currentDirection.next()
                    continue
                }
                Position(currentPosition.row + 1, currentPosition.column)
            }

            Direction.LEFT -> {
                if (currentPosition.column == 0) {
                    // Bye bye guard //
                    break
                }
                if (input[currentPosition.row][currentPosition.column - 1] == '#') {
                    // Obstacle //
                    currentDirection = currentDirection.next()
                    continue
                }
                Position(currentPosition.row, currentPosition.column - 1)
            }
        }
        positions.add(currentPosition)
    }

    return positions.size
}

private fun part2(input: List<String>): Int {
    var startingPosDir: PositionAndDirection? = null
    for (i in input.indices) {
        for (k in input[i].indices) {
            if (input[i][k] == '^') {
                startingPosDir = PositionAndDirection(row = i, column = k, direction = Direction.UP)
                break
            }
        }
    }
    val validObstaclesForLoops = mutableSetOf<Position>()
    for (i in input.indices) {
        for (k in input[i].indices) {
            val modifiedGrid = input.toMutableList()
            modifiedGrid[i] = String(input[i].toCharArray().also { it[k] = '#' })
            val posDirs = mutableSetOf<PositionAndDirection>()
            var currentPosDir = startingPosDir
            while (true) {
                currentPosDir ?: throw Exception()
                currentPosDir = when (currentPosDir.direction) {
                    Direction.UP -> {
                        if (currentPosDir.row == 0) {
                            // Bye bye guard //
                            break
                        }
                        if (modifiedGrid[currentPosDir.row - 1][currentPosDir.column] == '#') {
                            // Obstacle //
                            currentPosDir = currentPosDir.copy(direction = currentPosDir.direction.next())
                            continue
                        }
                        PositionAndDirection(currentPosDir.row - 1, currentPosDir.column, currentPosDir.direction)
                    }

                    Direction.RIGHT -> {
                        if (currentPosDir.column == modifiedGrid[0].length - 1) {
                            // Bye bye guard //
                            break
                        }
                        if (modifiedGrid[currentPosDir.row][currentPosDir.column + 1] == '#') {
                            // Obstacle //
                            currentPosDir = currentPosDir.copy(direction = currentPosDir.direction.next())
                            continue
                        }
                        PositionAndDirection(currentPosDir.row, currentPosDir.column + 1, currentPosDir.direction)
                    }

                    Direction.DOWN -> {
                        if (currentPosDir.row == modifiedGrid.size - 1) {
                            // Bye bye guard //
                            break
                        }
                        if (modifiedGrid[currentPosDir.row + 1][currentPosDir.column] == '#') {
                            // Obstacle //
                            currentPosDir = currentPosDir.copy(direction = currentPosDir.direction.next())
                            continue
                        }
                        PositionAndDirection(currentPosDir.row + 1, currentPosDir.column, currentPosDir.direction)
                    }

                    Direction.LEFT -> {
                        if (currentPosDir.column == 0) {
                            // Bye bye guard //
                            break
                        }
                        if (modifiedGrid[currentPosDir.row][currentPosDir.column - 1] == '#') {
                            // Obstacle //
                            currentPosDir = currentPosDir.copy(direction = currentPosDir.direction.next())
                            continue
                        }
                        PositionAndDirection(currentPosDir.row, currentPosDir.column - 1, currentPosDir.direction)
                    }
                }
                if (posDirs.contains(currentPosDir)) {
                    // Loop //
                    validObstaclesForLoops.add(Position(row = i, column = k))
                    break
                }
                posDirs.add(currentPosDir)
            }
        }
    }
    return validObstaclesForLoops.size
}

data class Position(val row: Int, val column: Int)

data class PositionAndDirection(val row: Int, val column: Int, val direction: Direction)

enum class Direction(private val index: Int) {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    fun next(): Direction {
        if (this == LEFT) return UP
        return values[index + 1]
    }

    companion object {
        private val values = Direction.entries.toList()
    }
}