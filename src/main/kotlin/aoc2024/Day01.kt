@file:Suppress("ktlint:standard:filename")

package aoc2024

import AdventOfCodeSolver
import utils.splitBySpace
import kotlin.math.abs

data object Day01Solver : AdventOfCodeSolver<Sequence<String>, Int> {
    /**
     * Compute the total distance.
     */
    override fun solvePart1(input: Sequence<String>): Int =
        input
            .map { it.pairOfIds() }
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
    override fun solvePart2(input: Sequence<String>): Int =
        input
            .map { it.pairOfIds() }
            .unzip()
            .let { (left, right) ->
                val rightIdsWithCount = right.groupingBy { it }.eachCount()
                left.sumOf { it * (rightIdsWithCount[it] ?: 0) }
            }
}

private fun String.pairOfIds(): Pair<Int, Int> = splitBySpace().map { it.toInt() }.let { (id1, id2) -> id1 to id2 }
