package leetcodes

fun main() {
    for (test in tests) {
        val atoi = myAtoi(test.first)
        println("Test for \"${test.first}\": $atoi")
        if (atoi != test.second) {
            throw Exception()
        }
    }
}

private val tests = listOf(
    "42" to 42,
    "-042" to -42,
    "1337c0d3" to 1337,
    "0-1" to 0,
    "words and 987" to 0,
    "-91283472332" to -2147483648,
    "+-12" to 0,
    "  +  413" to 0
)

private fun myAtoi(s: String): Int {
    var result: Long? = null
    var positive: Boolean? = null
    var overflowed = false

    for (char in s) {
        if (!char.isDigit()) {
            if (result == null) {
                when (char) {
                    ' ' -> continue
                    '+' -> if (positive != null) return 0 else {
                        positive = true
                        result = 0
                    }

                    '-' -> if (positive != null) return 0 else {
                        positive = false
                        result = 0
                    }

                    else -> break
                }
            } else {
                break
            }
        } else {
            if (char == '0' && result == null) {
                result = 0
                continue
            }
            result = result ?: 0
            val newResult = (result * 10) + char.digitToInt()
            if (newResult > Int.MAX_VALUE) {
                result = Int.MAX_VALUE.toLong()
                overflowed = true
                break
            }
            result = newResult
        }
    }

    if (result != null) {
        if (overflowed && positive == false) {
            return Int.MIN_VALUE
        }
        return result.toInt() * (if (positive != false) 1 else -1)
    }

    return 0
}
