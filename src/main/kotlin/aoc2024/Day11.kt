package aoc2024

import utils.splitBySpace
import utils.splitHalf

private fun String.hasAnEvenNumberOfDigits(): Boolean = this.length % 2 == 0

fun String.trimLeadingZeroes(): String = this.dropWhile { it == '0' }.ifEmpty { "0" }

private fun String.countStonesAfterBlinking(
    nb: Int,
    cache: MutableMap<Pair<String, Int>, Long>,
): Long =
    if (nb == 0) {
        1
    } else {
        cache.getOrPut(this to nb) {
            val nextNb = nb - 1
            when {
                this == "0" -> "1".countStonesAfterBlinking(nextNb, cache)
                this.hasAnEvenNumberOfDigits() -> {
                    val (left, right) = this.splitHalf()
                    left.countStonesAfterBlinking(nextNb, cache) +
                        right.trimLeadingZeroes().countStonesAfterBlinking(nextNb, cache)
                }
                else -> "${this.toLong() * 2024}".countStonesAfterBlinking(nextNb, cache)
            }
        }
    }

/**
 * Compute the number of stones after blinking 25 times
 */
fun String.solveDay11Part1(): Long {
    val cache = mutableMapOf<Pair<String, Int>, Long>()
    return this.splitBySpace().sumOf { it.countStonesAfterBlinking(25, cache) }
}

/**
 * Compute the number of stones after blinking 75 times
 */
fun String.solveDay11Part2(): Long {
    val cache = mutableMapOf<Pair<String, Int>, Long>()
    return this.splitBySpace().sumOf { it.countStonesAfterBlinking(75, cache) }
}
