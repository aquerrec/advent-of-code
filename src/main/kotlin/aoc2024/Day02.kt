package aoc2024

import AdventOfCodeSolver
import utils.splitBySpace
import kotlin.math.abs
import kotlin.math.sign

data object Day02Solver : AdventOfCodeSolver<Sequence<String>, Int> {
    /**
     * Count the number of safe reports.
     */
    override fun solvePart1(input: Sequence<String>): Int = input.map { it.toLevels() }.count { it.isSafeReport() }

    /**
     * Count the number of safe reports with a single bad level tolerance.
     */
    override fun solvePart2(input: Sequence<String>): Int =
        input
            .map { it.toLevels() }
            .count { levels ->
                levels.isSafeReport() || levels.allPossibilitiesRemovingOneLevel().any { it.isSafeReport() }
            }
}

private fun String.toLevels() = splitBySpace().map { it.toInt() }

private fun List<Int>.isSafeReport(): Boolean {
    val differences = zipWithNext { a, b -> b - a }
    return differences.all { it != 0 && abs(it) <= 3 } && differences.zipWithNext().all { (a, b) -> a.sign == b.sign }
}

fun List<Int>.allPossibilitiesRemovingOneLevel(): List<List<Int>> = indices.map { i -> filterIndexed { index, _ -> index != i } }
