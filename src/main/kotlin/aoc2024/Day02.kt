package aoc2024

import utils.splitBySpace
import kotlin.math.abs
import kotlin.math.sign

fun String.toLevels() = splitBySpace().map { it.toInt() }

fun List<Int>.isSafeReport(): Boolean {
    val differences = zipWithNext { a, b -> b - a }
    return differences.all { it != 0 && abs(it) <= 3 } && differences.zipWithNext().all { (a, b) -> a.sign == b.sign }
}

fun List<Int>.allPossibilitiesRemovingOneLevel(): List<List<Int>> = indices.map { i -> filterIndexed { index, _ -> index != i } }

/**
 * Count the number of safe reports.
 */
fun Sequence<String>.solveDay02Part1() = map { it.toLevels() }.count { it.isSafeReport() }

/**
 * Count the number of safe reports with a single bad level tolerance.
 */
fun Sequence<String>.solveDay02Part2() =
    map { it.toLevels() }
        .count { levels ->
            levels.isSafeReport() || levels.allPossibilitiesRemovingOneLevel().any { it.isSafeReport() }
        }
