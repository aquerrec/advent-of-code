package aoc2024

import utils.Matrix
import utils.removeAll
import utils.splitByEmptyLine
import kotlin.math.abs
import kotlin.math.roundToLong

/*
Day 13: Claw Contraption
 */

private const val BUTTON_A_COST = 3
private const val BUTTON_B_COST = 1

private fun String.parseClawMachines(): List<List<Pair<Long, Long>>> =
    this
        .splitByEmptyLine()
        .map { machine ->
            machine.lines().map {
                it.removeAll("Button A: X+", "Button B: X+", "Prize: X=", " Y+", " Y=").splitToLongPoint()
            }
        }

private fun String.splitToLongPoint(): Pair<Long, Long> = this.split(",").let { (x, y) -> x.toLong() to y.toLong() }

/**
 * Equation AX=Y
 * Where A is a 2x2 matrix, X and Y are 2x1 matrices.
 *
 * buttonAP * a.x + buttonBP * b.x = prize.x
 * buttonAP * a.y + buttonBP * b.y = prize.y
 */
private fun List<Pair<Long, Long>>.cost(prizeTranslation: Long = 0): Long =
    this.let { (buttonA, buttonB, prize) ->
        val (buttonAX, buttonAY) = buttonA
        val (buttonBX, buttonBY) = buttonB
        val (prizeX, prizeY) = prize
        val matrixA =
            Matrix(
                2,
                2,
                arrayOf(
                    buttonAX.toDouble(),
                    buttonBX.toDouble(),
                    buttonAY.toDouble(),
                    buttonBY.toDouble(),
                ),
            )
        val matrixY =
            Matrix(
                2,
                1,
                arrayOf(
                    (prizeX + prizeTranslation).toDouble(),
                    (prizeY + prizeTranslation).toDouble(),
                ),
            )
        Matrix
            .solveEquation(matrixA, matrixY)
            .let {
                val buttonAPressedTimes = it[0, 0].roundToLong()
                val buttonBPressedTimes = it[1, 0].roundToLong()
                val deltaA = abs(it[0, 0] - buttonAPressedTimes)
                val deltaB = abs(it[1, 0] - buttonBPressedTimes)
                if (deltaA < 0.001 && deltaB < 0.001) {
                    BUTTON_A_COST * buttonAPressedTimes + BUTTON_B_COST * buttonBPressedTimes
                } else {
                    0
                }
            }
    }

/**
 * Calculate the fewest tokens to spend to win all possible prizes
 */
fun String.solveDay13Part1(): Long = this.parseClawMachines().sumOf { it.cost() }

/**
 * Calculate the fewest tokens to spend to win all possible prizes after having fixed the conversion error
 */
fun String.solveDay13Part2(): Long = this.parseClawMachines().sumOf { it.cost(10000000000000) }
