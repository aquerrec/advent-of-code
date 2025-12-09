package utils

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Rectangle(
    val point1: Point,
    val point2: Point,
) {
    constructor(pair: Pair<Point, Point>) : this(pair.first, pair.second)

    val top: Int get() = min(point1.y, point2.y)
    val left: Int get() = min(point1.x, point2.x)
    val right: Int get() = max(point1.x, point2.x)
    val bottom: Int get() = max(point1.y, point2.y)

    val topLeft: Point get() = Point(min(point1.x, point2.x), min(point1.y, point2.y))
    val topRight: Point get() = Point(max(point1.x, point2.x), min(point1.y, point2.y))
    val bottomLeft: Point get() = Point(min(point1.x, point2.x), max(point1.y, point2.y))
    val bottomRight: Point get() = Point(max(point1.x, point2.x), max(point1.y, point2.y))

    operator fun contains(point: Point): Boolean = point.x in point1.x..point2.x && point.y in point1.y..point2.y

    fun area(): Long =
        (abs(point2.x - point1.x).toLong() + 1L) *
            (abs(point2.y - point1.y).toLong() + 1L)

    fun innerRectangle(): Rectangle = Rectangle(topLeft.move(Direction.DOWN_RIGHT), bottomRight.move(Direction.UP_LEFT))

    infix fun overlaps(other: Rectangle): Boolean =
        max(this.left, other.left) <= min(this.right, other.right) &&
            max(this.top, other.top) <= min(this.bottom, other.bottom)

    fun isFlat(): Boolean = point1.x == point2.x || point1.y == point2.y
}
