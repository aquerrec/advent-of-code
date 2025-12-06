package utils

import kotlin.math.pow

/**
 * A matrix of elements of type [T].
 * IMPORTANT! if thinking of the list of lists as a coordinate grid, row = y and col = x
 */
class Matrix<T>(
    inputMatrix: List<List<T>>,
) {
    constructor(
        rows: Int,
        columns: Int,
        data: Array<T>,
    ) : this(
        List(rows) { row ->
            List(columns) { col ->
                data[row * columns + col]
            }
        },
    )

    data class Cell<T>(
        val coordinate: Point,
        val value: T,
    )

    private val matrix = MutableList(inputMatrix.size) { i -> inputMatrix[i].toMutableList() }

    val rows = matrix.size
    val columns = matrix.first().size

    val rowsIndices = matrix.indices
    val columnsIndices = matrix.first().indices

    init {
        require(inputMatrix.isNotEmpty()) { "Matrix must have at least one row" }
        require(inputMatrix.first().isNotEmpty()) { "Matrix must have at least one column" }
        require(inputMatrix.all { it.size == columns }) { "Matrix must have the same number of columns in each row" }
    }

    fun contains(
        row: Int,
        col: Int,
    ): Boolean = row in matrix.indices && col in matrix.first().indices

    operator fun contains(point: Point) = contains(point.y, point.x)

    /**
     * Returns the entire row at the given [row] index.
     * Index is 0-based.
     */
    operator fun get(row: Int): List<T> = matrix[row].toList()

    /**
     * Returns the element at the given [row] and [col].
     * Indexes are 0-based.
     */
    operator fun get(
        row: Int,
        col: Int,
    ): T = matrix[row][col]

    operator fun get(point: Point): T = matrix[point.y][point.x]

    /**
     * Returns the element at the given [row] and [col], null if out of bounds.
     * Indexes are 0-based.
     */
    fun getOrNull(
        row: Int,
        col: Int,
    ): T? = matrix.getOrNull(row)?.getOrNull(col)

    fun getOrNull(point: Point): T? = getOrNull(point.y, point.x)

    fun getCellOrNull(
        row: Int,
        col: Int,
    ): Cell<T>? = this.getOrNull(row, col)?.let { Cell(Point(col, row), it) }

    fun getCellOrNull(point: Point) = getCellOrNull(point.y, point.x)

    /**
     * Returns a new matrix with the element at the given [point] set to [value].
     */
    fun copyWithCellValue(
        point: Point,
        value: T,
    ): Matrix<T> {
        val newMatrix = this.copy()
        newMatrix.matrix[point.y][point.x] = value
        return newMatrix
    }

    /**
     * Returns a new matrix with the elements at the given [points] set to [value].
     */
    fun copyWithCellsValue(
        points: Collection<Point>,
        value: T,
    ): Matrix<T> {
        val newMatrix = this.copy()
        points.filter { it in this }.forEach { newMatrix.matrix[it.y][it.x] = value }
        return newMatrix
    }

    /**
     * Mutates the cell at the given [point] set to [value].
     */
    fun setCell(
        point: Point,
        value: T,
    ): T {
        val old = get(point)
        matrix[point.y][point.x] = value
        return old
    }

    fun findAllByValue(value: T): List<Point> = findAllByValueMatching { it == value }.map { it.coordinate }

    fun findAllByValueMatching(predicate: (T) -> Boolean): List<Cell<T>> =
        matrix
            .flatMapIndexed { rowIndex, columns ->
                columns.mapIndexedNotNull { colIndex, cellValue ->
                    val cell = Cell(Point(colIndex, rowIndex), cellValue)
                    if (predicate(cellValue)) cell else null
                }
            }

    fun findAllCells(predicate: (Cell<T>) -> Boolean): List<Cell<T>> =
        matrix
            .flatMapIndexed { rowIndex, columns ->
                columns.mapIndexedNotNull { colIndex, cellValue ->
                    val cell = Cell(Point(colIndex, rowIndex), cellValue)
                    if (predicate(cell)) cell else null
                }
            }

    fun first(value: T): Point? = firstOrNull { it == value }

    fun first(predicate: (T) -> Boolean): Point =
        firstOrNull(predicate)
            ?: throw NoSuchElementException("Matrix contains no element matching the predicate.")

    fun firstOrNull(predicate: (T) -> Boolean): Point? {
        matrix
            .forEachIndexed { rowIndex, columns ->
                columns.forEachIndexed { colIndex, value ->
                    if (predicate(value)) return Point(colIndex, rowIndex)
                }
            }
        return null
    }

    fun forEachRowIndexed(action: (rowIndex: Int, row: List<T>) -> Unit) = matrix.forEachIndexed(action)

    fun forEachRowIndexedReversed(action: (rowIndex: Int, row: List<T>) -> Unit) =
        rowsIndices.reversed().forEach { rowIdx -> action(rowIdx, row(rowIdx)) }

    fun <OUT> mapRowIndexed(action: (rowIndex: Int, row: List<T>) -> OUT) = rowsIndices.map { rowIdx -> action(rowIdx, row(rowIdx)) }

    fun <OUT> mapRowIndexedReversed(action: (rowIndex: Int, row: List<T>) -> OUT) =
        rowsIndices.reversed().map { rowIdx -> action(rowIdx, row(rowIdx)) }

    /**
     * Returns the row at the given [row] index.
     * Index is 0-based.
     */
    fun row(row: Int): List<T> = matrix[row]

    /**
     * Returns the column at the given [col] index.
     * Index is 0-based.
     */
    fun column(col: Int): List<T> = List(matrix.size) { row -> matrix[row][col] }

    fun <OUT> mapRow(transform: (List<T>) -> List<OUT>): Matrix<OUT> = Matrix(matrix.map { transform(it) })

    /**
     * Return items in matrix above, below, left, right of given indexes
     * Does NOT include the center item
     * Does NOT return diagonals if [includeDiagonal] is false
     * Excludes out of bounds coordinates
     */
    fun neighbors(
        row: Int,
        col: Int,
        includeDiagonal: Boolean = false,
    ): List<T> =
        listOfNotNull(
            this.getOrNull(row - 1, col),
            this.getOrNull(row + 1, col),
            this.getOrNull(row, col - 1),
            this.getOrNull(row, col + 1),
            if (includeDiagonal) this.getOrNull(row - 1, col - 1) else null,
            if (includeDiagonal) this.getOrNull(row - 1, col + 1) else null,
            if (includeDiagonal) this.getOrNull(row + 1, col - 1) else null,
            if (includeDiagonal) this.getOrNull(row + 1, col + 1) else null,
        )

    /**
     * Return items in matrix above, below, left, right of given indexes
     * Does NOT include the center item
     * Does NOT return diagonals if [includeDiagonal] is false
     * Excludes out of bounds points
     */
    fun neighborsCells(
        row: Int,
        col: Int,
        includeDiagonal: Boolean = false,
        predicate: (Cell<T>) -> Boolean = { true },
    ): List<Cell<T>> =
        listOfNotNull(
            getCellOrNull(row - 1, col),
            getCellOrNull(row + 1, col),
            getCellOrNull(row, col - 1),
            getCellOrNull(row, col + 1),
            if (includeDiagonal) getCellOrNull(row - 1, col - 1) else null,
            if (includeDiagonal) getCellOrNull(row - 1, col + 1) else null,
            if (includeDiagonal) getCellOrNull(row + 1, col - 1) else null,
            if (includeDiagonal) getCellOrNull(row + 1, col + 1) else null,
        ).filter { predicate(it) }

    fun neighborsCells(
        point: Point,
        includeDiagonal: Boolean = false,
        predicate: (Cell<T>) -> Boolean = { true },
    ) = neighborsCells(point.y, point.x, includeDiagonal, predicate)

    fun neighborsCells(
        cell: Cell<T>,
        includeDiagonal: Boolean = false,
        predicate: (Cell<T>) -> Boolean = { true },
    ) = neighborsCells(cell.coordinate, includeDiagonal, predicate)

    fun subMatrixRows(
        fromIndex: Int,
        toIndex: Int,
    ) = Matrix(matrix.subList(fromIndex, toIndex))

    fun take(n: Int) = Matrix(matrix.take(n))

    /**
     * Transpose this matrix.
     */
    fun transpose() =
        Matrix(
            List(matrix[0].size) { MutableList(matrix.size) { matrix[0][0] } }.also {
                matrix.forEachIndexed { rowIdx, row ->
                    row.forEachIndexed { colIdx, value ->
                        it[colIdx][rowIdx] = value
                    }
                }
            },
        )

    fun groupByValue(): Map<T, List<Point>> =
        matrix
            .flatMapIndexed { rowIndex, columns ->
                columns.mapIndexedNotNull { colIndex, cellValue ->
                    cellValue.takeIf { it != null }?.let { Point(colIndex, rowIndex) to cellValue }
                }
            }.groupBy({ it.second }) { it.first }

    fun copy() = Matrix(matrix)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix<*>) return false

        if (matrix != other.matrix) return false

        return true
    }

    override fun hashCode(): Int = matrix.hashCode()

    override fun toString(): String = matrix.joinToString("\n") { it.joinToString(" ") }

    companion object {
        /**
         * Returns an identity matrix of order [n].
         */
        fun identity(n: Int): Matrix<Double> = Matrix(List(n) { row -> List(n) { col -> if (row == col) 1.0 else 0.0 } })

        fun <T : Number> solveEquation(
            left: Matrix<T>,
            right: Matrix<T>,
        ): Matrix<Double> = left.inverse().x(right)
    }
}

fun <T> List<List<T>>.toMatrix() = Matrix(this)

fun Sequence<String>.toMatrix() = Matrix(map { it.asSequence().toList() }.toList())

/**
 * Returns the determinant of the matrix.
 * Uses the rule of Sarrus.
 * Only works for square matrices.
 */
fun <T : Number> Matrix<T>.determinant(): Double {
    require(this.rows == this.columns) { "Matrix must be square" }

    when (this.rows) {
        1 -> {
            return this[0, 0].toDouble()
        }

        2 -> {
            return this[0, 0].toDouble() * this[1, 1].toDouble() - this[0, 1].toDouble() * this[1, 0].toDouble()
        }

        else -> {
            var det = 0.0
            for (c in 0 until this.columns) {
                var incValue = 1.0
                var decValue = 1.0

                for (r in this.rowsIndices) {
                    incValue *= this[r, (c + r) % this.columns].toDouble()
                    decValue *= this[this.rows - r - 1, (c + r) % this.columns].toDouble()
                }
                det += (incValue - decValue)
            }
            return det
        }
    }
}

operator fun <T : Number> Matrix<T>.times(other: Number): Matrix<Double> =
    Matrix(
        List(rows) { row ->
            List(columns) { col ->
                this[row, col].toDouble() * other.toDouble()
            }
        },
    )

/**
 * Calculates an inverse matrix of a square matrix.
 */
fun <T : Number> Matrix<T>.inverse(): Matrix<Double> {
    require(this.rows == this.columns) { "Matrix must be square" }

    val det = this.determinant()
    return if (det == 0.0) Matrix.identity(this.rows) else this.adjoint() * (det.pow(-1))
}

/**
 * Returns the adjoint matrix of this matrix.
 */
fun <T : Number> Matrix<T>.adjoint(): Matrix<Double> {
    require(this.rows == this.columns) { "Matrix must be square" }

    return Matrix(
        List(rows) { row ->
            List(columns) { col ->
                val sign = (-1.0).pow(row + col)
                val cofactorDet = minorMatrix(row, col).determinant()
                sign * cofactorDet
            }
        },
    ).transpose()
}

/**
 * Returns the minor matrix of this matrix.
 * Minors are obtained by removing just one row and one column from square matrices. (first minors)
 */
fun <T : Number> Matrix<T>.minorMatrix(
    row: Int,
    col: Int,
): Matrix<T> =
    if (rows < 2 || columns < 2 || row >= rows || col >= columns) {
        throw IllegalArgumentException("Index out of bound")
    } else {
        Matrix(
            List(rows - 1) { r ->
                List(columns - 1) { c ->
                    val rowIndex = if (r >= row) r + 1 else r
                    val colIndex = if (c >= col) c + 1 else c
                    this[rowIndex, colIndex]
                }
            },
        )
    }

/**
 * Cross product of two matrices.
 */
infix fun <A : Number, B : Number> Matrix<A>.x(other: Matrix<B>): Matrix<Double> {
    require(this.columns == other.rows)

    return Matrix(
        List(rows) { i ->
            List(other.columns) { j ->
                (0 until columns).sumOf { k -> this[i, k].toDouble() * other[k, j].toDouble() }
            }
        },
    )
}
