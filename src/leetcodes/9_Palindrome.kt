package leetcodes

import kotlin.math.pow

fun main() {
    for (test in tests) {
        val mySolution = isPalindrome(test.first)
        println("Test for \"${test.first}\": $mySolution")
        if (mySolution != test.second) {
            throw Exception()
        }
    }
}

private val tests = listOf(
    121 to true,
    -121 to false,
    10 to false
)

private fun isPalindrome(x: Int): Boolean {
    if (x < 0) return false
    return isSame(x, 1000000000, 10)
            && isSame(x, 100000000, 100)
            && isSame(x, 100000000, 1000)
            && isSame(x, 10000000, 10000)
            && isSame(x, 1000000, 100000)
}

private fun isSame(x: Int, leftDivider: Int, rightDivider: Int): Boolean {
    for (i in 9 downTo 0) {
        var divider = 1.0
        for (k in 0..i) {
            if (x / divider.pow(k) == 0.0) {
                continue
            }
        }
    }


    val left = x / leftDivider
    if (left > 0) {
        val right = x.mod(rightDivider)
        return left == right
    }
    return false
}
