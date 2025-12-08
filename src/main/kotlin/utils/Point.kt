package utils

import utils.Direction.DOWN
import utils.Direction.DOWN_LEFT
import utils.Direction.DOWN_RIGHT
import utils.Direction.LEFT
import utils.Direction.RIGHT
import utils.Direction.UP
import utils.Direction.UP_LEFT
import utils.Direction.UP_RIGHT
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 2D point. Coordinates are from top-left corner.
 */
data class Point(
    val x: Int = 0,
    val y: Int = 0,
) {
    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    fun move(
        direction: Direction,
        distance: Int = 1,
    ): Point =
        when (direction) {
            UP -> copy(y = y - distance)
            DOWN -> copy(y = y + distance)
            LEFT -> copy(x = x - distance)
            RIGHT -> copy(x = x + distance)
            UP_LEFT -> copy(x = x - distance, y = y - distance)
            UP_RIGHT -> copy(x = x + distance, y = y - distance)
            DOWN_LEFT -> copy(x = x - distance, y = y + distance)
            DOWN_RIGHT -> copy(x = x + distance, y = y + distance)
        }

    fun move(
        x: Int,
        y: Int,
    ) = Point(this.x + x, this.y + y)

    fun moveWithinBoundaries(
        x: Int,
        y: Int,
        boundaries: Boundaries,
        goOtherSide: Boolean = false,
    ): Point {
        val newX =
            (this.x + x).let {
                if (goOtherSide) Math.floorMod(it, boundaries.sizeX) else it.coerceIn(0, boundaries.maxX)
            }
        val newY =
            (this.y + y).let {
                if (goOtherSide) Math.floorMod(it, boundaries.sizeY) else it.coerceIn(0, boundaries.maxY)
            }
        return Point(newX, newY)
    }

    operator fun plus(other: Point) = Point(x + other.x, y + other.y)

    operator fun minus(other: Point) = Point(x - other.x, y - other.y)

    /**
     * Returns the neighbors of this point in directions UP, DOWN, LEFT, RIGHT, excluding this point.
     */
    fun neighbors(): List<Point> = listOf(UP, DOWN, LEFT, RIGHT).map { move(it) }

    /**
     * Returns a 3x3 matrix with all neighbors of this point, including this point at the center if [includeSelf] is true.
     */
    fun allNeighbors(includeSelf: Boolean = true): List<List<Point>> =
        listOf(
            listOf(move(-1, -1), move(0, -1), move(1, -1)),
            listOfNotNull(move(-1, 0), this.takeIf { includeSelf }, move(1, 0)),
            listOf(move(-1, 1), move(0, 1), move(1, 1)),
        )

    /**
     * Returns the direction to the given adjacent point.
     */
    infix fun directionToAdjacent(other: Point): Direction =
        if (x == other.x) {
            if (y - 1 == other.y) UP else DOWN
        } else if (y == other.y) {
            if (x + 1 == other.x) RIGHT else LEFT
        } else if (x - 1 == other.x) {
            if (y - 1 == other.y) UP_LEFT else DOWN_LEFT
        } else if (x + 1 == other.x) {
            if (y - 1 == other.y) UP_RIGHT else DOWN_RIGHT
        } else {
            throw IllegalStateException("Point $other is not adjacent to $this")
        }

    /**
     * Rotate the point by the given [rotation] degrees, a multiple of 90 degrees only.
     * Positive values rotate clockwise, negative values rotate counterclockwise.
     */
    infix fun rotate(rotation: Int): Point =
        when (rotation % 360) {
            0 -> this
            90 -> Point(y, -x)
            180 -> Point(-x, -y)
            270 -> Point(-y, x)
            else -> throw IllegalStateException("Point $this does not support rotation $rotation")
        }

    fun reverse(): Point = Point(y, x)

    /**
     * Returns the list of all points in a rectangle from this point to the target point, treating the two points as corners.
     */
    fun enumerateRectangle(toPoint: Point): List<Point> {
        val (x1, x2) = listOf(this.x, toPoint.x).sorted()
        val (y1, y2) = listOf(this.y, toPoint.y).sorted()
        return (x1..x2).flatMap { x -> (y1..y2).map { y -> Point(x, y) } }
    }

    /**
     * Returns the list of all points in a line from this point to the target point, including both start and end points.
     * Points are in order from this to [toPoint]
     * Ex: (0,0) to (5,-5) produces: [(0,0), (1,-1), (2,-2), (3,-3), (4,-4), (5,-5)]
     * Only produces straight lines for vertical, horizontal, and 45deg lines
     * Ex: (0,0) to (5,2) produces: [(0,0), (1,1), (2,2), (3,2), (4,2), (5,2)]
     */
    fun enumerateLine(toPoint: Point): List<Point> =
        (x toward toPoint.x padTo lengthY(toPoint))
            .zip(y toward toPoint.y padTo lengthX(toPoint))
            .map(::Point)

    fun lengthX(toPoint: Point) = abs(x - toPoint.x) + 1

    fun lengthY(toPoint: Point) = abs(y - toPoint.y) + 1

    /**
     * Returns the Pythagorean distance to the given point.
     */
    fun distance(to: Point): Double = sqrt((to.x - x).toDouble().pow(2) + (to.y - y).toDouble().pow(2))

    /**
     * Returns the Manhattan distance to the given point.
     */
    fun manhattanDistance(to: Point): Int = abs(to.x - x) + abs(to.y - y)

    override fun toString(): String = "($x, $y)"
}

data class Boundaries(
    val maxX: Int,
    val maxY: Int,
) {
    val sizeX = maxX + 1
    val sizeY = maxY + 1

    companion object {
        fun ofSize(
            sizeX: Int,
            sizeY: Int,
        ) = Boundaries(sizeX - 1, sizeY - 1)
    }
}

data class Rectangle(
    val topLeft: Point,
    val bottomRight: Point,
) {
    operator fun contains(point: Point): Boolean = point.x in topLeft.x..bottomRight.x && point.y in topLeft.y..bottomRight.y
}

/**
 * Creates an in-order list of all points visited by drawing a line from each Point to the next
 * Ex: [(0,0), (2,0), (5,5)] -> [(0,0), (1,0), (2,0), (3,1), (4,2), (5,3), (5,4), (5,5)]
 * Note: duplicates ARE maintained! If line segments intersect, all passes through that point are maintained
 * Can be useful for testing if a path doubles back on itself (and where)
 */
fun List<Point>.enumerateLines(): List<Point> = zipWithNext { a, b -> a.enumerateLine(b).dropLast(1) }.flatten().plus(last())

/**
 * Creates a set of every Point intersected by two List<Point> (path)
 * Optionally, provide a set of Points to ignore (ex: if both paths start at the same point, but you don't want that to count)
 * Note: no duplicate intersections (set)
 */
fun List<Point>.intersections(
    other: List<Point>,
    filterPoints: Set<Point> = emptySet(),
): Set<Point> = enumerateLines().toSet().intersect(other.enumerateLines().toSet()).minus(filterPoints)

/**
 * Returns true if given point is contained by the rectangle formed by the pair of points
 */
fun Pair<Point, Point>.contains(p: Point): Boolean {
    val (x1, x2) = listOf(first.x, second.x).sorted()
    val (y1, y2) = listOf(first.y, second.y).sorted()
    return p.x in x1..x2 && p.y in y1..y2
}

/**
 * Creates two Points from a collection to describe the corners of a rectangle that contain all the Points
 */
fun Collection<Point>.bounds(): Pair<Point, Point> {
    val (x1, x2) = map { it.x }.sorted()
    val (y1, y2) = map { it.y }.sorted()
    return (Point(x1, y1) to Point(x2, y2))
}

fun Collection<Point>.maxX(): Int = maxOf { it.x }

fun Collection<Point>.maxY(): Int = maxOf { it.y }

fun Collection<Point>.minX(): Int = minOf { it.x }

fun Collection<Point>.minY(): Int = minOf { it.y }
