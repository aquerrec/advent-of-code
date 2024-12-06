package utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PointTest {
    @Test
    fun `should move the point in the given direction with default distance`() {
        Point(3, 5).move(Direction.UP) shouldBe Point(3, 4)
        Point(3, 5).move(Direction.DOWN) shouldBe Point(3, 6)
        Point(3, 5).move(Direction.LEFT) shouldBe Point(2, 5)
        Point(3, 5).move(Direction.RIGHT) shouldBe Point(4, 5)
        Point(3, 5).move(Direction.UP_LEFT) shouldBe Point(2, 4)
        Point(3, 5).move(Direction.UP_RIGHT) shouldBe Point(4, 4)
        Point(3, 5).move(Direction.DOWN_LEFT) shouldBe Point(2, 6)
        Point(3, 5).move(Direction.DOWN_RIGHT) shouldBe Point(4, 6)
    }

    @Test
    fun `should move the point in the given direction with the given distance`() {
        Point(3, 5).move(Direction.UP, 3) shouldBe Point(3, 2)
        Point(3, 5).move(Direction.DOWN, 3) shouldBe Point(3, 8)
        Point(3, 5).move(Direction.LEFT, 3) shouldBe Point(0, 5)
        Point(3, 5).move(Direction.RIGHT, 3) shouldBe Point(6, 5)
        Point(3, 5).move(Direction.UP_LEFT, 3) shouldBe Point(0, 2)
        Point(3, 5).move(Direction.UP_RIGHT, 3) shouldBe Point(6, 2)
        Point(3, 5).move(Direction.DOWN_LEFT, 3) shouldBe Point(0, 8)
        Point(3, 5).move(Direction.DOWN_RIGHT, 3) shouldBe Point(6, 8)
    }

    @Test
    fun `should move the point with the given x and y`() {
        Point(3, 5).move(2, 1) shouldBe Point(5, 6)
    }

    @Test
    fun `should addition two points`() {
        Point(3, 5) + Point(2, 1) shouldBe Point(5, 6)
    }

    @Test
    fun `should substract two points`() {
        Point(3, 5) - Point(2, 1) shouldBe Point(1, 4)
    }

    @Test
    fun `should return the neighbors`() {
        Point(3, 5).neighbors() shouldBe
            listOf(
                Point(3, 4),
                Point(3, 6),
                Point(2, 5),
                Point(4, 5),
            )
    }

    @Test
    fun `should return all the neighbors including self by default`() {
        Point(3, 5).allNeighbors() shouldBe
            listOf(
                listOf(Point(2, 4), Point(3, 4), Point(4, 4)),
                listOf(Point(2, 5), Point(3, 5), Point(4, 5)),
                listOf(Point(2, 6), Point(3, 6), Point(4, 6)),
            )
    }

    @Test
    fun `should return all the neighbors excluding self`() {
        Point(3, 5).allNeighbors(includeSelf = false) shouldBe
            listOf(
                listOf(Point(2, 4), Point(3, 4), Point(4, 4)),
                listOf(Point(2, 5), Point(4, 5)),
                listOf(Point(2, 6), Point(3, 6), Point(4, 6)),
            )
    }

    @Test
    fun `should return the direction to the adjacent point`() {
        Point(3, 5) directionToAdjacent Point(3, 4) shouldBe Direction.UP
        Point(3, 5) directionToAdjacent Point(3, 6) shouldBe Direction.DOWN
        Point(3, 5) directionToAdjacent Point(2, 5) shouldBe Direction.LEFT
        Point(3, 5) directionToAdjacent Point(4, 5) shouldBe Direction.RIGHT
        Point(3, 5) directionToAdjacent Point(2, 4) shouldBe Direction.UP_LEFT
        Point(3, 5) directionToAdjacent Point(4, 4) shouldBe Direction.UP_RIGHT
        Point(3, 5) directionToAdjacent Point(2, 6) shouldBe Direction.DOWN_LEFT
        Point(3, 5) directionToAdjacent Point(4, 6) shouldBe Direction.DOWN_RIGHT
    }

    @Test
    fun `should throw an exception while computing the direction to a point that is not adjacent`() {
        assertThrows<IllegalStateException> {
            Point(3, 5) directionToAdjacent Point(99, 99)
        }
    }

    @Test
    fun `should rotate the point by the given degrees`() {
        Point(3, 5) rotate 90 shouldBe Point(5, -3)
        Point(3, 5) rotate 180 shouldBe Point(-3, -5)
        Point(3, 5) rotate 270 shouldBe Point(-5, 3)
        Point(3, 5) rotate 360 shouldBe Point(3, 5)
    }

    @Test
    fun `should return the list of all points in a rectangle`() {
        Point(3, 5).enumerateRectangle(Point(5, 3)) shouldBe
            listOf(
                Point(3, 3),
                Point(3, 4),
                Point(3, 5),
                Point(4, 3),
                Point(4, 4),
                Point(4, 5),
                Point(5, 3),
                Point(5, 4),
                Point(5, 5),
            )
    }

    @Test
    fun `should return the list of all points in a line`() {
        Point(3, 5).enumerateLine(Point(5, 3)) shouldBe
            listOf(
                Point(3, 5),
                Point(4, 4),
                Point(5, 3),
            )
    }

    @Test
    fun `should return the x length between two points`() {
        Point(3, 5).lengthX(Point(5, 3)) shouldBe 3
        Point(3, 5).lengthX(Point(3, 3)) shouldBe 1
    }

    @Test
    fun `should return the y length between two points`() {
        Point(3, 5).lengthY(Point(5, 3)) shouldBe 3
        Point(3, 5).lengthY(Point(5, 5)) shouldBe 1
    }
}
