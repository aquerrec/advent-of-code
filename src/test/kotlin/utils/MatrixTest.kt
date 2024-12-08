package utils

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MatrixTest {
    private val matrixListOfLists =
        listOf(
            listOf("A1", "B1", "C1"),
            listOf("A2", "B2", "C2"),
            listOf("A3", "B3", "C3"),
            listOf("A4", "B4", "C4"),
            listOf("A5", "B5", "C5"),
        )
    private val matrix = Matrix(matrixListOfLists)

    @Test
    fun `should return true or false if the matrix contains the given cell`() {
        matrix.contains(1, 2) shouldBe true
        matrix.contains(99, 99) shouldBe false
    }

    @Test
    fun `should return true or false if the matrix contains the given point`() {
        matrix.contains(Point(2, 1)) shouldBe true
        matrix.contains(Point(99, 99)) shouldBe false
    }

    @Test
    fun `should get an item of the matrix`() {
        matrix[1, 2] shouldBe "C2"
        matrix[1, 3] shouldBe null
        matrix[Point(2, 1)] shouldBe "C2"
        matrix[Point(3, 1)] shouldBe null
    }

    @Test
    fun `should get a pair of the point and value of the cell in the matrix`() {
        matrix.getPointOrNull(1, 2) shouldBe Pair(Point(2, 1), "C2")
        matrix.getPointOrNull(1, 3) shouldBe null
        matrix.getPointOrNull(Point(2, 1)) shouldBe Pair(Point(2, 1), "C2")
        matrix.getPointOrNull(Point(3, 1)) shouldBe null
    }

    @Test
    fun `should return a new matrix and have changed the desired cell with the given value`() {
        matrix.setPoint(Point(2, 1), "XX") shouldBe
            Matrix(
                listOf(
                    listOf("A1", "B1", "C1"),
                    listOf("A2", "B2", "XX"),
                    listOf("A3", "B3", "C3"),
                    listOf("A4", "B4", "C4"),
                    listOf("A5", "B5", "C5"),
                ),
            )
    }

    @Test
    fun `should return a new matrix and have changed the desired cells with the given value`() {
        matrix.setPoints(
            listOf(
                Point(2, 1),
                Point(0, 2),
            ),
            "XX",
        ) shouldBe
            Matrix(
                listOf(
                    listOf("A1", "B1", "C1"),
                    listOf("A2", "B2", "XX"),
                    listOf("XX", "B3", "C3"),
                    listOf("A4", "B4", "C4"),
                    listOf("A5", "B5", "C5"),
                ),
            )
    }

    @Test
    fun `should return all the points with the given value`() {
        Matrix(
            listOf(
                listOf("A", "B", "C"),
                listOf("D", "A", "F"),
                listOf("G", "A", "I"),
            ),
        ).findAll("A") shouldBe listOf(Point(0, 0), Point(1, 1), Point(1, 2))
    }

    @Test
    fun `should return all the points matching the given predicate`() {
        (Point(0, 0) to 'A') shouldBe (Point(0, 0) to 'A')
        Matrix(
            listOf(
                listOf("A", "B", "C"),
                listOf("D", "A", "F"),
                listOf("G", "A", "I"),
            ),
        ).findAll { it == "A" } shouldBe
            listOf(
                Point(0, 0) to "A",
                Point(1, 1) to "A",
                Point(1, 2) to "A",
            )
    }

    @Test
    fun `should return the first point matching the given predicate`() {
        matrix.first { it == "B3" } shouldBe Point(1, 2)
    }

    @Test
    fun `should return the row`() {
        matrix.row(2) shouldBe listOf("A3", "B3", "C3")
    }

    @Test
    fun `should throw an exception when getting a row with an invalid index`() {
        shouldThrow<IndexOutOfBoundsException> {
            matrix.row(99)
        }
    }

    @Test
    fun `should return the column`() {
        matrix.column(1) shouldBe listOf("B1", "B2", "B3", "B4", "B5")
    }

    @Test
    fun `should throw an exception when getting a column with an invalid index`() {
        shouldThrow<IndexOutOfBoundsException> {
            matrix.column(99)
        }
    }

    @Test
    fun `should return the neighbors without diagonals`() {
        matrix.neighbors(2, 1) shouldBe listOf("B2", "B4", "A3", "C3")
        matrix.neighbors(0, 0) shouldBe listOf("A2", "B1")
        matrix.neighbors(0, 2) shouldBe listOf("C2", "B1")
        matrix.neighbors(2, 0) shouldBe listOf("A2", "A4", "B3")
        matrix.neighbors(4, 0) shouldBe listOf("A4", "B5")
        matrix.neighbors(4, 2) shouldBe listOf("C4", "B5")
        matrix.neighbors(99, 99) shouldBe emptyList()
    }

    @Test
    fun `should return the neighbors with diagonals`() {
        matrix.neighbors(2, 1, true) shouldBe listOf("B2", "B4", "A3", "C3", "A2", "C2", "A4", "C4")
        matrix.neighbors(0, 0, true) shouldBe listOf("A2", "B1", "B2")
        matrix.neighbors(0, 2, true) shouldBe listOf("C2", "B1", "B2")
        matrix.neighbors(2, 0, true) shouldBe listOf("A2", "A4", "B3", "B2", "B4")
        matrix.neighbors(4, 0, true) shouldBe listOf("A4", "B5", "B4")
        matrix.neighbors(4, 2, true) shouldBe listOf("C4", "B5", "B4")
        matrix.neighbors(99, 99, true) shouldBe emptyList()
    }

    @Test
    fun `should return the neighbors points without diagonals`() {
        matrix.neighborsPoints(2, 1) shouldBe
            listOf(
                Point(1, 1) to "B2",
                Point(1, 3) to "B4",
                Point(0, 2) to "A3",
                Point(2, 2) to "C3",
            )
        matrix.neighborsPoints(0, 0) shouldBe
            listOf(
                Point(0, 1) to "A2",
                Point(1, 0) to "B1",
            )
        matrix.neighborsPoints(0, 2) shouldBe
            listOf(
                Point(2, 1) to "C2",
                Point(1, 0) to "B1",
            )
        matrix.neighborsPoints(2, 0) shouldBe
            listOf(
                Point(0, 1) to "A2",
                Point(0, 3) to "A4",
                Point(1, 2) to "B3",
            )
        matrix.neighborsPoints(4, 0) shouldBe
            listOf(
                Point(0, 3) to "A4",
                Point(1, 4) to "B5",
            )
        matrix.neighborsPoints(4, 2) shouldBe
            listOf(
                Point(2, 3) to "C4",
                Point(1, 4) to "B5",
            )
        matrix.neighborsPoints(99, 99) shouldBe emptyList()
    }

    @Test
    fun `should return the neighbors points with diagonals`() {
        matrix.neighborsPoints(2, 1, true) shouldBe
            listOf(
                Point(1, 1) to "B2",
                Point(1, 3) to "B4",
                Point(0, 2) to "A3",
                Point(2, 2) to "C3",
                Point(0, 1) to "A2",
                Point(2, 1) to "C2",
                Point(0, 3) to "A4",
                Point(2, 3) to "C4",
            )
        matrix.neighborsPoints(0, 0, true) shouldBe
            listOf(
                Point(0, 1) to "A2",
                Point(1, 0) to "B1",
                Point(1, 1) to "B2",
            )
        matrix.neighborsPoints(0, 2, true) shouldBe
            listOf(
                Point(2, 1) to "C2",
                Point(1, 0) to "B1",
                Point(1, 1) to "B2",
            )
        matrix.neighborsPoints(2, 0, true) shouldBe
            listOf(
                Point(0, 1) to "A2",
                Point(0, 3) to "A4",
                Point(1, 2) to "B3",
                Point(1, 1) to "B2",
                Point(1, 3) to "B4",
            )
        matrix.neighborsPoints(4, 0, true) shouldBe
            listOf(
                Point(0, 3) to "A4",
                Point(1, 4) to "B5",
                Point(1, 3) to "B4",
            )
        matrix.neighborsPoints(4, 2, true) shouldBe
            listOf(
                Point(2, 3) to "C4",
                Point(1, 4) to "B5",
                Point(1, 3) to "B4",
            )
        matrix.neighborsPoints(99, 99, true) shouldBe emptyList()
    }

    @Test
    fun `should transpose the given matrix`() {
        matrix.transpose() shouldBe
            Matrix(
                listOf(
                    listOf("A1", "A2", "A3", "A4", "A5"),
                    listOf("B1", "B2", "B3", "B4", "B5"),
                    listOf("C1", "C2", "C3", "C4", "C5"),
                ),
            )
    }
}
