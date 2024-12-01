package y2023

import println
import readInput
import java.math.BigInteger


// Types values //
private const val FIVE_OF_A_KIND: Byte = 0x07
private const val FOUR_OF_A_KIND: Byte = 0x06
private const val FULL_HOUSE: Byte = 0x05
private const val THREE_OF_A_KIND: Byte = 0x04
private const val TWO_PAIR: Byte = 0x03
private const val ONE_PAIR: Byte = 0x02
private const val HIGH_CARD: Byte = 0x01

fun main() {
    fun part1(input: List<String>): String {
        val singleCardsMap = mapOf<Char, Byte>(
            'A' to 0x0E,
            'K' to 0x0D,
            'Q' to 0x0C,
            'J' to 0x0B,
            'T' to 0x0A,
            '9' to 0x09,
            '8' to 0x08,
            '7' to 0x07,
            '6' to 0x06,
            '5' to 0x05,
            '4' to 0x04,
            '3' to 0x03,
            '2' to 0x02,
        )
        val handsAndBets = mutableListOf<Pair<Long, Int>>()
        for (line in input) {
            val array = byteArrayOf(0, 0, 0, 0, 0, 0)
            val cardMap = mutableMapOf<Char, Int>()
            val handAndBet = line.split(" ")
            handAndBet[0].forEachIndexed { i, card ->
                cardMap[card] = cardMap[card]?.plus(1) ?: 1
                array[i + 1] = singleCardsMap[card]!!
            }
            val orderedValues = cardMap.values.sortedDescending()
            array[0] = when (orderedValues[0]) {
                5 -> FIVE_OF_A_KIND
                4 -> FOUR_OF_A_KIND
                3 -> if (orderedValues[1] == 2) {
                    FULL_HOUSE
                } else {
                    THREE_OF_A_KIND
                }

                2 -> if (orderedValues[1] == 2) {
                    TWO_PAIR
                } else {
                    ONE_PAIR
                }

                else -> HIGH_CARD
            }

            handsAndBets.add(BigInteger(array).toLong() to handAndBet[1].toInt())
        }

        var sum = 0L
        handsAndBets.sortedBy { it.first }.forEachIndexed { i, hand ->
            sum += (i + 1) * hand.second
        }

        return sum.toString()
    }

    fun part2(input: List<String>): String {
        val singleCardsMap = mapOf<Char, Byte>(
            'A' to 0x0E,
            'K' to 0x0D,
            'Q' to 0x0C,
            'T' to 0x0A,
            '9' to 0x09,
            '8' to 0x08,
            '7' to 0x07,
            '6' to 0x06,
            '5' to 0x05,
            '4' to 0x04,
            '3' to 0x03,
            '2' to 0x02,
            'J' to 0x01,
        )
        val handsAndBets = mutableListOf<Pair<Long, Int>>()
        for (line in input) {
            val array = byteArrayOf(0, 0, 0, 0, 0, 0)
            val cardMap = mutableMapOf<Char, Int>()
            val handAndBet = line.split(" ")
            handAndBet[0].forEachIndexed { i, card ->
                cardMap[card] = cardMap[card]?.plus(1) ?: 1
                array[i + 1] = singleCardsMap[card]!!
            }
            array[0] = evaluateSecondPartCard(cardMap)

            handsAndBets.add(BigInteger(array).toLong() to handAndBet[1].toInt())
        }

        var sum = 0L
        handsAndBets.sortedBy { it.first }.forEachIndexed { i, hand ->
            sum += (i + 1) * hand.second
        }

        return sum.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == "6440")
    check(part2(testInput) == "5905")

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

fun evaluateSecondPartCard(cardMap: Map<Char, Int>): Byte {
    val orderedValues = cardMap.values.sortedDescending()
    val jokers = cardMap['J'] ?: 0
    return when (orderedValues[0]) {
        5 -> FIVE_OF_A_KIND
        4 -> if (jokers > 0) FIVE_OF_A_KIND else FOUR_OF_A_KIND
        3 -> when (jokers) {
            1 -> FOUR_OF_A_KIND
            2 -> FIVE_OF_A_KIND
            3 -> if (orderedValues[1] == 2) {
                FIVE_OF_A_KIND
            } else {
                FOUR_OF_A_KIND
            }

            else -> if (orderedValues[1] == 2) {
                FULL_HOUSE
            } else {
                THREE_OF_A_KIND
            }
        }

        2 -> when (jokers) {
            2 -> if (orderedValues[1] == 2) {
                FOUR_OF_A_KIND
            } else {
                THREE_OF_A_KIND
            }

            1 -> if (orderedValues[1] == 2) {
                FULL_HOUSE
            } else {
                THREE_OF_A_KIND
            }

            else -> if (orderedValues[1] == 2) {
                TWO_PAIR
            } else {
                ONE_PAIR
            }
        }

        else -> if (jokers == 1) {
            ONE_PAIR
        } else {
            HIGH_CARD
        }
    }
}