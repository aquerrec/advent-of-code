package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readMatrixOfInt

class Day10Test {
    private val sampleInput = readMatrixOfInt("2024/day10/sample.txt")
    private val puzzleInput = readMatrixOfInt("2024/day10/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay10Part1() shouldBe 36
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay10Part1() shouldBe 535
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay10Part2() shouldBe 81
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay10Part2() shouldBe 1186
        }
    }
}
