fun main() {
    fun part1(input: List<String>): String {
        val instructions = input[0]
        val map = mutableMapOf<String, Array<String>>()
        val regex = "([A-Z]+)".toRegex()
        for (i in 2 until input.size) {
            val matches = regex.findAll(input[i]).toList()
            map[matches[0].value] = arrayOf(matches[1].value, matches[2].value)
        }

        var steps = 0L
        var current = "AAA"
        while (current != "ZZZ") {
            for (instruction in instructions) {
                current = if (instruction == 'L') {
                    map[current]!![0]
                } else {
                    map[current]!![1]
                }
                steps++
            }
        }

        return steps.toString()
    }

    fun part2(input: List<String>): String {
        val instructions = input[0]
        val map = mutableMapOf<String, Array<String>>()
        val regex = "([A-Z0-9]+)".toRegex()
        val currentPositions = mutableListOf<String>()
        for (i in 2 until input.size) {
            val matches = regex.findAll(input[i]).toList()
            map[matches[0].value] = arrayOf(matches[1].value, matches[2].value)

            if (matches[0].value.endsWith('A')) {
                currentPositions.add(matches[0].value)
            }
        }

        val stepList = mutableListOf<Long>()
        for (position in currentPositions) {
            var current = position
            var steps = 0L
            while (current[2] != 'Z') {
                for (instruction in instructions) {
                    current = if (instruction == 'L') {
                        map[current]!![0]
                    } else {
                        map[current]!![1]
                    }
                    steps++
                }
            }
            stepList.add(steps)
        }

        return findLCMOfListOfNumbers(stepList).toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == "6")
    val test2Input = readInput("Day08_test_2")
    check(part2(test2Input) == "6")

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
