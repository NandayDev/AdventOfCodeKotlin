import java.math.BigInteger


// Types values //
const val fiveOfAKind: Byte = 0x07
const val fourOfAKind: Byte = 0x06
const val fullHouse: Byte = 0x05
const val threeOfAKind: Byte = 0x04
const val twoPair: Byte = 0x03
const val onePair: Byte = 0x02
const val highCard: Byte = 0x01

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
                5 -> fiveOfAKind
                4 -> fourOfAKind
                3 -> if (orderedValues[1] == 2) {
                    fullHouse
                } else {
                    threeOfAKind
                }

                2 -> if (orderedValues[1] == 2) {
                    twoPair
                } else {
                    onePair
                }

                else -> highCard
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
        5 -> fiveOfAKind
        4 -> if (jokers > 0) fiveOfAKind else fourOfAKind
        3 -> when (jokers) {
            1 -> fourOfAKind
            2 -> fiveOfAKind
            3 -> if (orderedValues[1] == 2) {
                fiveOfAKind
            } else {
                fourOfAKind
            }

            else -> if (orderedValues[1] == 2) {
                fullHouse
            } else {
                threeOfAKind
            }
        }

        2 -> when (jokers) {
            2 -> if (orderedValues[1] == 2) {
                fourOfAKind
            } else {
                threeOfAKind
            }

            1 -> if (orderedValues[1] == 2) {
                fullHouse
            } else {
                threeOfAKind
            }

            else -> if (orderedValues[1] == 2) {
                twoPair
            } else {
                onePair
            }
        }

        else -> if (jokers == 1) {
            onePair
        } else {
            highCard
        }
    }
}