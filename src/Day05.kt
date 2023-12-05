fun main() {
    fun part1(input: List<String>): UInt {
        var seeds = input[0].split(": ")[1].split(" ").map { it.toUInt() }
        val modifiers = mutableListOf<Modifier>()

        fun updateSeeds() {
            val newSeeds = mutableListOf<UInt>()
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
                    val values = line.split(" ").map { it.toUInt() }
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

    fun part2(input: List<String>): UInt {
        val seeds = input[0].split(": ")[1].split(" ").map { it.toUInt() }
        val modifiers = mutableListOf<MutableList<Modifier>>(
            mutableListOf()
        )

        for (line in input.subList(1, input.size)) {
            when {
                line == "" -> {
                    // End of map //
                    modifiers.add(mutableListOf())
                }
                line.contains("map") -> {}
                else -> {
                    val values = line.split(" ").map { it.toUInt() }
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

        var minResult = UInt.MAX_VALUE

        for (i in seeds.indices step 2) {
            val seed = seeds[i]
            val range = seeds[i + 1]
            for (s in seed until seed + range) {
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

        return minResult
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toUInt())
    check(part2(testInput) == 46.toUInt())
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class Modifier(
    val fromIncl: UInt,
    val toExcl: UInt,
    val addendum: UInt
)