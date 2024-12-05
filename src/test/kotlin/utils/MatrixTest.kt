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
    fun `should get an item of the matrix`() {
        matrix[1, 2] shouldBe "C2"
        matrix[1, 3] shouldBe null
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
