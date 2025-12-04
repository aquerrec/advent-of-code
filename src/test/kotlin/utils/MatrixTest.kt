package utils

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.Matrix.Cell

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
        matrix.getOrNull(1, 3) shouldBe null
        matrix[Point(2, 1)] shouldBe "C2"
        matrix.getOrNull(Point(3, 1)) shouldBe null
    }

    @Test
    fun `should get a cell in the matrix`() {
        matrix.getCellOrNull(1, 2) shouldBe Cell(Point(2, 1), "C2")
        matrix.getCellOrNull(1, 3) shouldBe null
        matrix.getCellOrNull(Point(2, 1)) shouldBe Cell(Point(2, 1), "C2")
        matrix.getCellOrNull(Point(3, 1)) shouldBe null
    }

    @Test
    fun `should return a new matrix and have changed the desired cell with the given value`() {
        matrix.copyWithCellValue(Point(2, 1), "XX") shouldBe
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
        matrix.copyWithCellsValue(
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
    fun `should change the desired cell value with the given value and return the old value`() {
        matrix.copy().setCell(Point(2, 1), "XX") shouldBe "C2"
    }

    @Test
    fun `should return all the points with the given value`() {
        Matrix(
            listOf(
                listOf("A", "B", "C"),
                listOf("D", "A", "F"),
                listOf("G", "A", "I"),
            ),
        ).findAllByValue("A") shouldBe
            listOf(
                Point(0, 0),
                Point(1, 1),
                Point(1, 2),
            )
    }

    @Test
    fun `should return all the points matching the given predicate for the value`() {
        Matrix(
            listOf(
                listOf("A", "B", "C"),
                listOf("D", "A", "F"),
                listOf("G", "A", "I"),
            ),
        ).findAllByValueMatching { it == "A" } shouldBe
            listOf(
                Cell(Point(0, 0), "A"),
                Cell(Point(1, 1), "A"),
                Cell(Point(1, 2), "A"),
            )
    }

    @Test
    fun `should return the first point matching the given value`() {
        matrix.first("B3") shouldBe Point(1, 2)
    }

    @Test
    fun `should return the first point matching the given predicate`() {
        matrix.first { it == "B3" } shouldBe Point(1, 2)
    }

    @Test
    fun `should throw when there is no cell matching the given predicate`() {
        assertThrows<NoSuchElementException> {
            matrix.first { it == "Z9" }
        }
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
    fun `should return the neighbors cells without diagonals`() {
        matrix.neighborsCells(2, 1) shouldBe
            listOf(
                Cell(Point(1, 1), "B2"),
                Cell(Point(1, 3), "B4"),
                Cell(Point(0, 2), "A3"),
                Cell(Point(2, 2), "C3"),
            )
        matrix.neighborsCells(0, 0) shouldBe
            listOf(
                Cell(Point(0, 1), "A2"),
                Cell(Point(1, 0), "B1"),
            )
        matrix.neighborsCells(0, 2) shouldBe
            listOf(
                Cell(Point(2, 1), "C2"),
                Cell(Point(1, 0), "B1"),
            )
        matrix.neighborsCells(2, 0) shouldBe
            listOf(
                Cell(Point(0, 1), "A2"),
                Cell(Point(0, 3), "A4"),
                Cell(Point(1, 2), "B3"),
            )
        matrix.neighborsCells(4, 0) shouldBe
            listOf(
                Cell(Point(0, 3), "A4"),
                Cell(Point(1, 4), "B5"),
            )
        matrix.neighborsCells(4, 2) shouldBe
            listOf(
                Cell(Point(2, 3), "C4"),
                Cell(Point(1, 4), "B5"),
            )
        matrix.neighborsCells(99, 99) shouldBe emptyList()
    }

    @Test
    fun `should return the neighbors cells with diagonals`() {
        matrix.neighborsCells(2, 1, true) shouldBe
            listOf(
                Cell(Point(1, 1), "B2"),
                Cell(Point(1, 3), "B4"),
                Cell(Point(0, 2), "A3"),
                Cell(Point(2, 2), "C3"),
                Cell(Point(0, 1), "A2"),
                Cell(Point(2, 1), "C2"),
                Cell(Point(0, 3), "A4"),
                Cell(Point(2, 3), "C4"),
            )
        matrix.neighborsCells(0, 0, true) shouldBe
            listOf(
                Cell(Point(0, 1), "A2"),
                Cell(Point(1, 0), "B1"),
                Cell(Point(1, 1), "B2"),
            )
        matrix.neighborsCells(0, 2, true) shouldBe
            listOf(
                Cell(Point(2, 1), "C2"),
                Cell(Point(1, 0), "B1"),
                Cell(Point(1, 1), "B2"),
            )
        matrix.neighborsCells(2, 0, true) shouldBe
            listOf(
                Cell(Point(0, 1), "A2"),
                Cell(Point(0, 3), "A4"),
                Cell(Point(1, 2), "B3"),
                Cell(Point(1, 1), "B2"),
                Cell(Point(1, 3), "B4"),
            )
        matrix.neighborsCells(4, 0, true) shouldBe
            listOf(
                Cell(Point(0, 3), "A4"),
                Cell(Point(1, 4), "B5"),
                Cell(Point(1, 3), "B4"),
            )
        matrix.neighborsCells(4, 2, true) shouldBe
            listOf(
                Cell(Point(2, 3), "C4"),
                Cell(Point(1, 4), "B5"),
                Cell(Point(1, 3), "B4"),
            )
        matrix.neighborsCells(99, 99, true).shouldBeEmpty()
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
