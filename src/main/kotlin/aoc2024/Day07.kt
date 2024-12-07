package aoc2024

import utils.splitBySpace

private fun String.parseTestValueAndNumbers(): Pair<Long, List<Long>> =
    this.split(": ").let { (testValue, numbers) ->
        testValue.toLong() to numbers.splitBySpace().map { it.toLong() }
    }

private fun isValid(
    testValue: Long,
    currentCalculation: Long,
    numbers: List<Long>,
    tryConcatOperator: Boolean = false,
): Boolean {
    val nextNumber = numbers.first()
    if (numbers.size == 1) {
        return currentCalculation * nextNumber == testValue ||
            currentCalculation + nextNumber == testValue ||
            (tryConcatOperator && "$currentCalculation$nextNumber".toLong() == testValue)
    }
    val remainingNumbers = numbers.drop(1)
    return isValid(testValue, currentCalculation * nextNumber, remainingNumbers, tryConcatOperator) ||
        isValid(testValue, currentCalculation + nextNumber, remainingNumbers, tryConcatOperator) ||
        (tryConcatOperator && isValid(testValue, "$currentCalculation$nextNumber".toLong(), remainingNumbers, true))
}

/**
 * Compute the total calibration result (with addition and multiplication)
 */
fun Sequence<String>.solveDay07Part1(): Long =
    map { it.parseTestValueAndNumbers() }
        .filter { (testValue, numbers) ->
            isValid(
                testValue = testValue,
                currentCalculation = numbers.first(),
                numbers = numbers.drop(1),
            )
        }.sumOf { (testValue) -> testValue }

/**
 * Compute the total calibration result (with addition, multiplication and concatenation)
 */
fun Sequence<String>.solveDay07Part2(): Long =
    map { it.parseTestValueAndNumbers() }
        .filter { (testValue, numbers) ->
            isValid(
                testValue = testValue,
                currentCalculation = numbers.first(),
                numbers = numbers.drop(1),
                tryConcatOperator = true,
            )
        }.sumOf { (testValue) -> testValue }
