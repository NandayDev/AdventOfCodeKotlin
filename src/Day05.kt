fun main() {
    fun part1(input: List<String>): Long {
        var seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }
        val modifiers = mutableListOf<Modifier>()

        fun updateSeeds() {
            val newSeeds = mutableListOf<Long>()
            for (seed in seeds) {
                var newSeed = seed
                for (modifier in modifiers) {
                    if (seed >= modifier.fromIncl && seed < modifier.toExcl) {
                        newSeed = seed + modifier.addendum
                        break
                    }
                }
                newSeeds.add(newSeed)
            }
            seeds = newSeeds
            modifiers.clear()
        }

        for (line in input.subList(1, input.size)) {
            when {
                line == "" -> {
                    // End of map //
                    updateSeeds()
                }
                line.contains("map") -> {}
                else -> {
                    val values = line.split(" ").map { it.toLong() }
                    modifiers.add(
                        Modifier(
                            fromIncl = values[1],
                            toExcl = values[1] + values[2],
                            addendum = values[0] - values[1]
                        )
                    )
                }
            }
        }
        updateSeeds()
        return seeds.min()
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }
        val modifiers = mutableListOf<MutableList<Modifier>>()

        for (line in input.subList(1, input.size)) {
            when {
                line == "" -> {
                    // End of map //
                    modifiers.add(mutableListOf())
                }
                line.contains("map") -> {}
                else -> {
                    val values = line.split(" ").map { it.toLong() }
                    modifiers[modifiers.size - 1].add(
                        Modifier(
                            fromIncl = values[1],
                            toExcl = values[1] + values[2],
                            addendum = values[0] - values[1]
                        )
                    )
                }
            }
        }

        var minResult = Long.MAX_VALUE
        val threads = mutableListOf<Thread>()

        for (i in seeds.indices step 2) {
            val thread = Thread {
                val initialSeed = seeds[i]
                val range = seeds[i + 1]
                for (s in initialSeed until initialSeed + range) {
                    var current = s
                    for (modifierList in modifiers) {
                        for (modifier in modifierList) {
                            if (current >= modifier.fromIncl && current < modifier.toExcl) {
                                current += modifier.addendum
                                break
                            }
                        }
                    }
                    minResult = minOf(minResult, current)
                }
            }
            thread.start()
            threads.add(thread)
        }

        threads.forEach { it.join() }

        return minResult
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class Modifier(
    val fromIncl: Long,
    val toExcl: Long,
    val addendum: Long
)