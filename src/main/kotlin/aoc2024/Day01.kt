package aoc2024

import utils.splitBySpace
import kotlin.math.abs

fun String.pairOfIds(): Pair<Int, Int> = splitBySpace().map { it.toInt() }.let { (id1, id2) -> id1 to id2 }

/**
 * Compute the total distance.
 */
fun Sequence<String>.solveDay01Part1(): Int =
    map { it.pairOfIds() }
        .unzip()
        .let {
            it.first
                .sorted()
                .zip(it.second.sorted())
                .sumOf { (a, b) -> abs(b - a) }
        }

/**
 * Compute the similarity score.
 */
fun Sequence<String>.solveDay01Part2(): Int {
    val (left, right) = map { it.pairOfIds() }.unzip()

    val rightIdsWithCount = right.groupingBy { it }.eachCount()

    return left.sumOf { it * (rightIdsWithCount[it] ?: 0) }
}
