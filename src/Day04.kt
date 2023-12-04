fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val parts = line.split(": ")[1].split("|")
            val winningNumbers = parts[0].split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val numbers = parts[1].split(" ").mapNotNull { it.toIntOrNull() }
            val wins = winningNumbers.intersect(numbers.toSet())
            var points = 0
            for (i in wins.indices) {
                if (points == 0) {
                    points++
                } else {
                    points *= 2
                }
            }
            sum += points
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<Int, Int>()
        input.forEachIndexed { i, line ->
            if (!map.containsKey(i + 1)) {
                map[i + 1] = 1
            } else {
                map[i + 1] = map[i + 1]!! + 1
            }
            val parts = line.split(": ")[1].split("|")
            val winningNumbers = parts[0].split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val numbers = parts[1].split(" ").mapNotNull { it.toIntOrNull() }
            val wins = winningNumbers.intersect(numbers.toSet())
            for (j in wins.indices) {
                for (k in 0 until map[i + 1]!!) {
                    val index = j + i + 2
                    if (!map.containsKey(index)) {
                        map[index] = 0
                    }
                    map[index] = map[index]!! + 1
                }
            }
        }
        return map.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
