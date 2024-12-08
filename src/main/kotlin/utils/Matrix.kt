package utils

/**
 * A matrix of elements of type [T].
 * IMPORTANT! if thinking of the list of lists as a coordinate grid, row = y and col = x
 */
class Matrix<T>(
    inputMatrix: List<List<T>>,
) {
    private val matrix = MutableList(inputMatrix.size) { i -> inputMatrix[i].toMutableList() }

    val rowsIndices = matrix.indices
    val columnsIndices = matrix.first().indices

    val rowsSize = matrix.size
    val columnsSize = matrix.first().size

    init {
        require(inputMatrix.isNotEmpty()) { "Matrix must have at least one row" }
        require(inputMatrix.first().isNotEmpty()) { "Matrix must have at least one column" }
        require(inputMatrix.all { it.size == columnsSize }) { "Matrix must have the same number of columns in each row" }
    }

    fun contains(
        row: Int,
        col: Int,
    ): Boolean = row in matrix.indices && col in matrix.first().indices

    operator fun contains(point: Point) = contains(point.y, point.x)

    /**
     * Returns the element at the given [row] and [col], null if out of bounds.
     * Indexes are 0-based.
     */
    operator fun get(
        row: Int,
        col: Int,
    ): T? =
        if (row < 0 || row >= matrix.size || col < 0 || col >= matrix[row].size) {
            null
        } else {
            matrix[row][col]
        }

    operator fun get(point: Point): T? = get(point.y, point.x)

    fun getPointOrNull(
        row: Int,
        col: Int,
    ): Pair<Point, T>? = this[row, col]?.let { Point(col, row) to it }

    fun getPointOrNull(point: Point) = getPointOrNull(point.y, point.x)

    /**
     * Returns a new matrix with the element at the given [point] set to [value].
     */
    fun setPoint(
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
    fun setPoints(
        points: Collection<Point>,
        value: T,
    ): Matrix<T> {
        val newMatrix = this.copy()
        points.filter { it in this }.forEach { newMatrix.matrix[it.y][it.x] = value }
        return newMatrix
    }

    fun findAll(value: T): List<Point> =
        matrix
            .flatMapIndexed { rowIndex, columns ->
                columns.mapIndexedNotNull { colIndex, cellValue ->
                    cellValue.takeIf { it == value }?.let { Point(colIndex, rowIndex) }
                }
            }

    fun findAll(predicate: (T) -> Boolean): List<Pair<Point, T>> =
        matrix
            .flatMapIndexed { rowIndex, columns ->
                columns.mapIndexedNotNull { colIndex, cellValue ->
                    if (predicate(cellValue)) Point(colIndex, rowIndex) to cellValue else null
                }
            }

    fun first(predicate: (T) -> Boolean): Point {
        matrix
            .forEachIndexed { rowIndex, columns ->
                columns.forEachIndexed { colIndex, value ->
                    if (predicate(value)) return Point(colIndex, rowIndex)
                }
            }
        throw NoSuchElementException("Matrix contains no element matching the predicate.")
    }

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
            this[row - 1, col],
            this[row + 1, col],
            this[row, col - 1],
            this[row, col + 1],
            if (includeDiagonal) this[row - 1, col - 1] else null,
            if (includeDiagonal) this[row - 1, col + 1] else null,
            if (includeDiagonal) this[row + 1, col - 1] else null,
            if (includeDiagonal) this[row + 1, col + 1] else null,
        )

    /**
     * Return items in matrix above, below, left, right of given indexes
     * Does NOT include the center item
     * Does NOT return diagonals if [includeDiagonal] is false
     * Excludes out of bounds points
     */
    fun neighborsPoints(
        row: Int,
        col: Int,
        includeDiagonal: Boolean = false,
    ): List<Pair<Point, T>> =
        listOfNotNull(
            getPointOrNull(row - 1, col),
            getPointOrNull(row + 1, col),
            getPointOrNull(row, col - 1),
            getPointOrNull(row, col + 1),
            if (includeDiagonal) getPointOrNull(row - 1, col - 1) else null,
            if (includeDiagonal) getPointOrNull(row - 1, col + 1) else null,
            if (includeDiagonal) getPointOrNull(row + 1, col - 1) else null,
            if (includeDiagonal) getPointOrNull(row + 1, col + 1) else null,
        )

    fun neighborsPoints(
        point: Point,
        includeDiagonal: Boolean = false,
    ) = neighborsPoints(point.y, point.x, includeDiagonal)

    fun transpose() =
        Matrix(
            MutableList(matrix[0].size) { MutableList(matrix.size) { matrix[0][0] } }.also {
                matrix.forEachIndexed { rowIdx, row ->
                    row.forEachIndexed { colIdx, value ->
                        it[colIdx][rowIdx] = value
                    }
                }
            },
        )

    fun copy() = Matrix(matrix)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix<*>) return false

        if (matrix != other.matrix) return false

        return true
    }

    override fun hashCode(): Int = matrix.hashCode()

    override fun toString(): String = matrix.joinToString("\n") { it.joinToString(" ") }
}

fun <T> List<List<T>>.toMatrix() = Matrix(this)

fun Sequence<String>.toMatrix() = Matrix(map { it.asSequence().toList() }.toList())
