package y2023

import println
import readInput
import kotlin.math.abs

fun main() {
    fun resolve(expansion: Int, input: List<String>): String {
        val emptyYIndexes = input.indices.toMutableSet()
        val emptyXIndexes = input[0].indices.toMutableSet()
        val points = mutableListOf<GalaxyPoint>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char != '.') {
                    emptyXIndexes.remove(x)
                    emptyYIndexes.remove(y)
                    points.add(GalaxyPoint(y, x))
                }
            }
        }

        var distanceSum = 0L

        for (firstPoint in points) {
            for (secondPoint in points) {
                if (firstPoint == secondPoint) continue
                distanceSum += abs(firstPoint.x - secondPoint.x) + abs(firstPoint.y - secondPoint.y)

                if (firstPoint.x > secondPoint.x) {
                    for (x in secondPoint.x..firstPoint.x) {
                        if (x in emptyXIndexes) {
                            distanceSum += expansion - 1
                        }
                    }
                } else {
                    for (x in secondPoint.x downTo firstPoint.x) {
                        if (x in emptyXIndexes) {
                            distanceSum += expansion - 1
                        }
                    }
                }

                if (firstPoint.y > secondPoint.y) {
                    for (y in secondPoint.y..firstPoint.y) {
                        if (y in emptyYIndexes) {
                            distanceSum += expansion - 1
                        }
                    }
                } else {
                    for (y in secondPoint.y downTo firstPoint.y) {
                        if (y in emptyYIndexes) {
                            distanceSum += expansion - 1
                        }
                    }
                }
            }
        }

        return (distanceSum / 2).toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(resolve(2, testInput) == "374")
    check(resolve(10, testInput) == "1030")
    check(resolve(100, testInput) == "8410")

    val input = readInput("Day11")
    resolve(2, input).println()
    resolve(1000000, input).println()
}

private data class GalaxyPoint(
    val y: Int,
    val x: Int
)