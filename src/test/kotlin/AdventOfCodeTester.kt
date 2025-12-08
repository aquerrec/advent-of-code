import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Matrix
import utils.toMatrix
import java.io.BufferedReader
import kotlin.time.measureTimedValue

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
            measureTimedValue { solver.solvePart1(sampleInput) }.let { (answer, duration) ->
                println("part 1 - sample input took $duration")
                answer shouldBe expectedValues.samplePart1
            }
        }

        @Test
        fun `solves part 1 - puzzle input`() {
            measureTimedValue { solver.solvePart1(puzzleInput) }.let { (answer, duration) ->
                println("part 1 - puzzle input took $duration")
                answer shouldBe expectedValues.puzzlePart1
            }
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `solves part 2 - sample input`() {
            measureTimedValue { solver.solvePart2(sampleInput) }.let { (answer, duration) ->
                println("part 2 - sample input took $duration")
                answer shouldBe expectedValues.samplePart2
            }
        }

        @Test
        fun `solves part 2 - puzzle input`() {
            measureTimedValue { solver.solvePart2(puzzleInput) }.let { (answer, duration) ->
                println("part 2 - puzzle input took $duration")
                answer shouldBe expectedValues.puzzlePart2
            }
        }
    }
}

abstract class AdventOfCodeTesterWithAdditionalOptions<IN, OUT, OPTION>(
    year: Int,
    day: Int,
    fileReader: FileReader<IN>,
    private val solver: AdventOfCodeSolverWithAdditionalOptions<IN, OUT, OPTION>,
    private val additionalOptions: AdditionalOptions<OPTION>,
    private val expectedValues: ExpectedValues<OUT>,
) {
    data class AdditionalOptions<T>(
        val samplePart1: T,
        val puzzlePart1: T,
        val samplePart2: T,
        val puzzlePart2: T,
    )

    private val day: String = day.toString().padStart(2, '0')

    private val sampleInput = fileReader.read("$year/day${this.day}/sample.txt")
    private val puzzleInput = fileReader.read("$year/day${this.day}/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `solves part 1 - sample input`() {
            measureTimedValue { solver.solvePart1(sampleInput, additionalOptions.samplePart1) }.let { (answer, duration) ->
                println("part 1 - sample input took $duration")
                answer shouldBe expectedValues.samplePart1
            }
        }

        @Test
        fun `solves part 1 - puzzle input`() {
            measureTimedValue { solver.solvePart1(puzzleInput, additionalOptions.puzzlePart1) }.let { (answer, duration) ->
                println("part 1 - puzzle input took $duration")
                answer shouldBe expectedValues.puzzlePart1
            }
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `solves part 2 - sample input`() {
            measureTimedValue { solver.solvePart2(sampleInput, additionalOptions.samplePart2) }.let { (answer, duration) ->
                println("part 2 - sample input took $duration")
                answer shouldBe expectedValues.samplePart2
            }
        }

        @Test
        fun `solves part 2 - puzzle input`() {
            measureTimedValue { solver.solvePart2(puzzleInput, additionalOptions.puzzlePart2) }.let { (answer, duration) ->
                println("part 2 - puzzle input took $duration")
                answer shouldBe expectedValues.puzzlePart2
            }
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
