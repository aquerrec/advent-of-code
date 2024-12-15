package aoc2024

import utils.Boundaries
import utils.Point
import utils.Rectangle
import utils.removeAll
import utils.splitBySpace

/*
Day 14: Restroom Redoubt
 */

private fun Boundaries.quadrants() =
    listOf(
        Rectangle(topLeft = Point(0, 0), bottomRight = Point((sizeX / 2) - 1, (sizeY / 2) - 1)),
        Rectangle(topLeft = Point((sizeX / 2) + 1, 0), bottomRight = Point(maxX, (sizeY / 2) - 1)),
        Rectangle(topLeft = Point(0, (sizeY / 2) + 1), bottomRight = Point((sizeX / 2) - 1, maxY)),
        Rectangle(topLeft = Point((sizeX / 2) + 1, (sizeY / 2) + 1), bottomRight = Point(maxX, maxY)),
    )

private data class Robot(
    var position: Point,
    val velocity: Point,
) {
    fun move(boundaries: Boundaries) {
        position =
            position.moveWithinBoundaries(
                velocity.x,
                velocity.y,
                boundaries,
                goOtherSide = true,
            )
    }
}

private fun List<String>.parseRobots(): List<Robot> =
    this.map { line ->
        val (position, velocity) =
            line
                .removeAll("p=", "v=")
                .splitBySpace()
                .map { it.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
        Robot(position, velocity)
    }

private fun List<Robot>.areArrangedInAChristmasTree(boundaries: Boundaries): Boolean =
    List(boundaries.sizeX) { CharArray(boundaries.sizeY) { '.' } }
        .also { map ->
            this
                .forEach { robot ->
                    map[robot.position.x][robot.position.y] = '#'
                }
        }.any { row -> row.joinToString("").contains("#################################") }

/**
 * Compute the safety factor after exactly 100 seconds
 */
fun List<String>.solveDay14Part1(boundaries: Boundaries): Int =
    parseRobots()
        .apply { repeat(100) { forEach { robot -> robot.move(boundaries) } } }
        .let { robots ->
            boundaries
                .quadrants()
                .map { quadrant -> robots.count { quadrant.contains(it.position) } }
        }.reduce(Int::times)

/**
 * Search the fewest number of seconds that must elapse for the robots to display the Easter egg
 */
fun List<String>.solveDay14Part2(boundaries: Boundaries): Int {
    parseRobots()
        .also { robots ->
            repeat(10000) { seconds ->
                robots.forEach { it.move(boundaries) }
                if (robots.areArrangedInAChristmasTree(boundaries)) {
                    return seconds + 1
                }
            }
        }
    return 0
}
