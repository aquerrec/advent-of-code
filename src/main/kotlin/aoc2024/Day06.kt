package aoc2024

import utils.Direction
import utils.Matrix
import utils.Point
import utils.Turn.RIGHT

private const val START_CHAR = '^'
private const val OBSTRUCTION_CHAR = '#'

private sealed class GuardPatrolling {
    data class PatrolAndLeave(
        val positions: Set<Point>,
    ) : GuardPatrolling()

    class StuckInALoop : GuardPatrolling()
}

private fun Matrix<Char>.getGuardPatrolRoute(start: Point): GuardPatrolling {
    var currentPosition = start
    var direction = Direction.UP
    val allVisitedPoints = mutableMapOf<Point, List<Direction>>()

    while (this.contains(currentPosition)) {
        if (allVisitedPoints[currentPosition]?.contains(direction) == true) {
            // already visited in the same direction
            return GuardPatrolling.StuckInALoop()
        }

        allVisitedPoints.merge(currentPosition, listOf(direction), List<Direction>::plus)

        currentPosition =
            currentPosition.move(direction).let { next1 ->
                if (this[next1] == OBSTRUCTION_CHAR) {
                    direction = direction.turn(RIGHT)
                    currentPosition.move(direction).let { next2 ->
                        if (this[next2] == OBSTRUCTION_CHAR) {
                            direction = direction.turn(RIGHT)
                            currentPosition.move(direction)
                        } else {
                            next2
                        }
                    }
                } else {
                    next1
                }
            }
    }

    return GuardPatrolling.PatrolAndLeave(allVisitedPoints.keys)
}

/**
 * Count distinct positions visited by the guard before leaving the area.
 */
fun Matrix<Char>.solveDay06Part1(): Int =
    when (val guardPatrol = getGuardPatrolRoute(start = first { it == START_CHAR })) {
        is GuardPatrolling.PatrolAndLeave -> guardPatrol.positions.size
        is GuardPatrolling.StuckInALoop -> throw IllegalStateException("Guard is stuck in a loop")
    }

/**
 * Count different positions we could choose for an obstruction to make the guard stuck in a loop.
 */
fun Matrix<Char>.solveDay06Part2(): Int =
    first { it == START_CHAR }
        .let { startPosition ->
            (getGuardPatrolRoute(startPosition) as GuardPatrolling.PatrolAndLeave)
                .positions
                .filter { it != startPosition }
                .count {
                    this
                        .copyWithCellValue(it, OBSTRUCTION_CHAR)
                        .getGuardPatrolRoute(startPosition) is GuardPatrolling.StuckInALoop
                }
        }
