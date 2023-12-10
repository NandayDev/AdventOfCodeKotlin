fun main() {

    fun part1(input: List<String>): String {
        val (startingY, startingX) = getStartingPoints(input)
        val startingPoint = Point(startingX, startingY, 0, 0, startingX, startingY, 0)
        val loop = getLoop(input, startingPoint)
        return loop.maxDistance.toString()
    }

    fun part2(input: List<String>): String {
        val (startingY, startingX) = getStartingPoints(input)
        val startingPoint = Point(startingX, startingY, 0, 0, startingX, startingY, 0)
        val loop = getLoop(input, startingPoint)
        val loopMap = loop.points.associateBy { it.key }
        val emptyPoints = mutableListOf<Point>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == '.') {
                    emptyPoints.add(Point(0, 0, 0, 0, x, y, 0))
                }
            }
        }
        var totalArea = 0
        for (emptyPoint in emptyPoints) {
            val currentList = mutableListOf(emptyPoint)
            val checkedSet = mutableMapOf(emptyPoint.key to emptyPoint)
            while (true) {
                var foundNeighbours = 0
                var valid = true
                var area = 1
                for (currentPoint in currentList.toList()) {
                    for (point in listOf(
                        currentPoint.west(),
                        currentPoint.north(),
                        currentPoint.east(),
                        currentPoint.south())
                    ) {
                        if (point.key in checkedSet) continue
                        checkedSet[point.key] = point
                        when (point.getType(input)) {
                            Point.Type.OUT_OF_BOUNDS -> valid = false
                            Point.Type.NO_PIPE -> {
                                area++
                                currentList.add(point)
                                foundNeighbours++
                            }
                            else -> {
                                if (point.key !in loopMap) {
                                    valid = false
                                }
                            }
                        }
                        if (!valid) break
                    }
                    if (!valid) break
                }
                if (!valid) break
                if (foundNeighbours == 0) {
                    totalArea += area
                    break
                }
            }
        }
        return totalArea.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == "8")
    val test2Input = readInput("Day10_test2")
    check(part2(test2Input) == "8")

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

private fun getStartingPoints(input: List<String>): Pair<Int, Int> {
    var startingX = 0
    var startingY = 0
    var found = false
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] == 'S') {
                startingY = y
                startingX = x
                found = true
                break
            }
            if (found) break
        }
    }
    return startingY to startingX
}

private fun getLoop(
    input: List<String>,
    startingPoint: Point
): Loop {
    var maxLoop = listOf<Point>()
    var maxDistance = 0

    var westPoint: Point? = startingPoint.west()
    val westPointLoop = mutableListOf<Point>()
    while (true) {
        if (westPoint == null) {
            break
        }
        westPointLoop.add(westPoint)
        if (westPoint.isStartingPoint()) {
            val currentDistance = westPoint.currentDistance / 2
            if (maxDistance < currentDistance) {
                maxDistance = currentDistance
                maxLoop = westPointLoop
            }
            break
        }
        westPoint = getNextPoint(input, westPoint)?.also {
            westPointLoop.add(it)
        }
    }

    var southPoint: Point? = startingPoint.south()
    val southPointLoop = mutableListOf<Point>()
    while (true) {
        if (southPoint == null) {
            break
        }
        if (southPoint.isStartingPoint()) {
            val currentDistance = southPoint.currentDistance / 2
            if (maxDistance < currentDistance) {
                maxDistance = currentDistance
                maxLoop = southPointLoop
            }
            break
        }
        southPoint = getNextPoint(input, southPoint)?.also {
            southPointLoop.add(it)
        }
    }

    var eastPoint: Point? = startingPoint.east()
    val eastPointLoop = mutableListOf<Point>()
    while (true) {
        if (eastPoint == null) {
            break
        }
        if (eastPoint.isStartingPoint()) {
            val currentDistance = eastPoint.currentDistance / 2
            if (maxDistance < currentDistance) {
                maxDistance = currentDistance
                maxLoop = eastPointLoop
            }
            break
        }
        eastPoint = getNextPoint(input, eastPoint)?.also {
            eastPointLoop.add(it)
        }
    }

    var northPoint: Point? = startingPoint.north()
    val northPointLoop = mutableListOf<Point>()
    while (true) {
        if (northPoint == null) {
            break
        }
        if (northPoint.isStartingPoint()) {
            val currentDistance = northPoint.currentDistance / 2
            if (maxDistance < currentDistance) {
                maxDistance = currentDistance
                maxLoop = northPointLoop
            }
            break
        }
        northPoint = getNextPoint(input, northPoint)?.also {
            northPointLoop.add(it)
        }
    }
    return Loop(maxLoop, maxDistance)
}

private fun getNextPoint(
    input: List<String>,
    point: Point,
    log: Boolean = false,
): Point? {
    if (log) {
        println(point.toString())
    }
    return when (point.getType(input)) {
        Point.Type.NORTH_SOUTH -> when (point.previousY) {
            point.currentY + 1 -> point.north()
            point.currentY - 1 -> point.south()
            else -> null // previous pipe not connected to this one
        }
        Point.Type.WEST_EAST -> when (point.previousX) {
            point.currentX + 1 -> point.west()
            point.currentX - 1 -> point.east()
            else -> null // previous pipe not connected to this one
        }
        Point.Type.NORTH_EAST -> when {
            point.previousY == point.currentY - 1 -> point.east()
            point.previousX == point.currentX + 1 -> point.north()
            else -> null // previous pipe not connected to this one
        }
        Point.Type.NORTH_WEST -> when {
            point.previousY == point.currentY - 1 -> point.west()
            point.previousX == point.currentX - 1 -> point.north()
            else -> null // previous pipe not connected to this one
        }
        Point.Type.SOUTH_WEST -> when {
            point.previousY == point.currentY + 1 -> point.west()
            point.previousX == point.currentX - 1 -> point.south()
            else -> null // previous pipe not connected to this one
        }
        Point.Type.SOUTH_EAST -> when {
            point.previousY == point.currentY + 1 -> point.east()
            point.previousX == point.currentX + 1 -> point.south()
            else -> null // previous pipe not connected to this one
        }
        Point.Type.NO_PIPE, Point.Type.OUT_OF_BOUNDS, Point.Type.STARTING_POINT -> null // No pipe or invalid
    }
}

data class Point(
    val startingX: Int,
    val startingY: Int,
    val previousX: Int,
    val previousY: Int,
    val currentX: Int,
    val currentY: Int,
    val currentDistance: Int
) {

    val key = "$currentY,$currentX"

    enum class Type {
        OUT_OF_BOUNDS,
        NO_PIPE,
        NORTH_SOUTH,
        WEST_EAST,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_EAST,
        SOUTH_WEST,
        STARTING_POINT
    }

    fun isStartingPoint() = currentX == startingX && currentY == startingY

    fun getType(input: List<String>): Type {
        if (currentX < 0 || currentX >= input[0].length || currentY < 0 || currentY >= input.size) {
            return Type.OUT_OF_BOUNDS
        }
        return when (input[currentY][currentX]) {
            '.' -> Type.NO_PIPE
            '|' -> Type.NORTH_SOUTH
            '-' -> Type.WEST_EAST
            'L' -> Type.NORTH_EAST
            'J' -> Type.NORTH_WEST
            '7' -> Type.SOUTH_WEST
            'F' -> Type.SOUTH_EAST
            'S' -> Type.STARTING_POINT
            else -> throw IllegalArgumentException()
        }
    }

    fun west() = Point(
        startingX = startingX,
        startingY = startingY,
        previousX = currentX,
        previousY = currentY,
        currentX = currentX - 1,
        currentY = currentY,
        currentDistance = currentDistance + 1
    )

    fun north() = Point(
        startingX = startingX,
        startingY = startingY,
        previousX = currentX,
        previousY = currentY,
        currentX = currentX,
        currentY = currentY - 1,
        currentDistance = currentDistance + 1
    )

    fun east() = Point(
        startingX = startingX,
        startingY = startingY,
        previousX = currentX,
        previousY = currentY,
        currentX = currentX + 1,
        currentY = currentY,
        currentDistance = currentDistance + 1
    )

    fun south() = Point(
        startingX = startingX,
        startingY = startingY,
        previousX = currentX,
        previousY = currentY,
        currentX = currentX,
        currentY = currentY + 1,
        currentDistance = currentDistance + 1
    )
}

data class Loop(
    val points: List<Point>,
    val maxDistance: Int
)