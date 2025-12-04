package aoc2024

import utils.Direction
import utils.toMatrix

/**
 * Count the number of XMAS words
 */
fun Sequence<String>.solveDay04Part1(): Int {
    val matrix = this.toMatrix()

    return matrix.findAllByValue('X').sumOf { xPoint ->
        matrix
            .neighborsCells(xPoint, true) { it.value == 'M' }
            .count { (mPoint) ->
                val direction = xPoint.directionToAdjacent(mPoint)
                matrix[mPoint.move(direction)] == 'A' &&
                    matrix[mPoint.move(direction).move(direction)] == 'S'
            }
    }
}

/**
 * Count the number of X-MAS
 */
fun Sequence<String>.solveDay04Part2(): Int {
    val matrix = this.toMatrix()

    return matrix.findAllByValue('A').count {
        val upLeft = matrix[it.move(Direction.UP_LEFT)]
        val upRight = matrix[it.move(Direction.UP_RIGHT)]
        val downRight = matrix[it.move(Direction.DOWN_RIGHT)]
        val downLeft = matrix[it.move(Direction.DOWN_LEFT)]

        (((upLeft == 'M') && (downRight == 'S')) || ((upLeft == 'S') && (downRight == 'M'))) &&
            (((upRight == 'M') && (downLeft == 'S')) || ((upRight == 'S') && (downLeft == 'M')))
    }
}
