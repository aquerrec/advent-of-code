import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Matrix
import utils.toMatrix
import java.io.BufferedReader

data class ExpectedValues<T>(
    val samplePart1: T,
    val puzzlePart1: T,
    val samplePart2: T,
    val puzzlePart2: T,
)

abstract class AdventOfCodeTester<IN, OUT>(
    year: Int,
    day: Int,
    fileReader: FileReader<IN>,
    private val solver: AdventOfCodeSolver<IN, OUT>,
    private val expectedValues: ExpectedValues<OUT>,
) {
    private val day: String = day.toString().padStart(2, '0')

    private val sampleInput = fileReader.read("$year/day${this.day}/sample.txt")
    private val puzzleInput = fileReader.read("$year/day${this.day}/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `solves part 1 - sample input`() {
            solver.solvePart1(sampleInput) shouldBe expectedValues.samplePart1
        }

        @Test
        fun `solves part 1 - puzzle input`() {
            solver.solvePart1(puzzleInput) shouldBe expectedValues.puzzlePart1
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `solves part 2 - sample input`() {
            solver.solvePart2(sampleInput) shouldBe expectedValues.samplePart2
        }

        @Test
        fun `solves part 2 - puzzle input`() {
            solver.solvePart2(puzzleInput) shouldBe expectedValues.puzzlePart2
        }
    }
}

sealed class FileReader<T>(
    val read: (filePath: String) -> T,
) {
    data object TextReader : FileReader<String>({ fromResources(it).readText() })

    data object ListReader : FileReader<List<String>>({ fromResources(it).readLines() })

    data object FirstLineReader : FileReader<String>({ fromResources(it).readLine() })

    data object SequenceReader : FileReader<Sequence<String>>({ ListReader.read(it).asSequence() })

    data object MatrixOfCharReader : FileReader<Matrix<Char>>(
        { filePath -> ListReader.read(filePath).map { it.toList() }.toMatrix() },
    )

    data object MatrixOfIntReader : FileReader<Matrix<Int>>({ filePath ->
        ListReader
            .read(filePath)
            .map { line -> line.map { it.digitToInt() } }
            .toMatrix()
    })
}

private fun fromResources(filePath: String): BufferedReader =
    AdventOfCodeTester::class.java.classLoader
        .getResourceAsStream(filePath)!!
        .bufferedReader()
