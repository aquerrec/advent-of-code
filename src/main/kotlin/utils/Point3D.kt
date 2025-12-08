package utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 3D point
 */
data class Point3D(
    val x: Int = 0,
    val y: Int = 0,
    val z: Int = 0,
) {
    fun move(
        x: Int,
        y: Int,
        z: Int,
    ) = Point3D(this.x + x, this.y + y, this.z + z)

    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Point3D) = Point3D(x - other.x, y - other.y, z - other.z)

    fun lengthX(toPoint: Point3D) = abs(x - toPoint.x) + 1

    fun lengthY(toPoint: Point3D) = abs(y - toPoint.y) + 1

    fun lengthZ(toPoint: Point3D) = abs(z - toPoint.z) + 1

    fun distance(to: Point3D): Double = sqrt((to.x - x).toDouble().pow(2) + (to.y - y).toDouble().pow(2) + (to.z - z).toDouble().pow(2))

    /**
     * Returns the Manhattan distance to the given point.
     */
    fun manhattanDistance(to: Point3D): Int = abs(to.x - x) + abs(to.y - y) + abs(to.z - z)

    override fun toString(): String = "($x, $y, $z)"
}
