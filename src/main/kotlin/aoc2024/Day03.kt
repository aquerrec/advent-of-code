package aoc2024

val mulInstructionRegex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
val mulDoDontInstructionsRegex = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)".toRegex()

fun String.extractMulInstructions() = mulInstructionRegex.findAll(this).map { it.value }

fun String.extractDoDontMulInstructions() = mulDoDontInstructionsRegex.findAll(this).map { it.value }

fun String.doMul(): Int =
    this.removePrefix("mul(").removeSuffix(")").split(",").let { (a, b) ->
        return a.toInt() * b.toInt()
    }

/**
 * Scan the corrupted memory for multiplication instructions.
 */
fun Sequence<String>.solveDay03Part1(): Int = flatMap { it.extractMulInstructions() }.sumOf { it.doMul() }

/**
 * Scan the corrupted memory for multiplication instructions with do/don't instructions.
 */
fun Sequence<String>.solveDay03Part2(): Int =
    flatMap { it.extractDoDontMulInstructions() }
        .fold(0 to true) { (sum, enabled), instruction ->
            when (instruction) {
                "do()" -> sum to true
                "don't()" -> sum to false
                else ->
                    if (enabled) {
                        sum + instruction.doMul() to true
                    } else {
                        sum to false
                    }
            }
        }.first
