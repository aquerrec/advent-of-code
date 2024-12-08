package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readMatrix

class Day08Test {
    private val sampleInput = readMatrix("2024/day08/sample.txt")
    private val puzzleInput = readMatrix("2024/day08/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay08Part1() shouldBe 14
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay08Part1() shouldBe 265
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay08Part2() shouldBe 34
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay08Part2() shouldBe 962
        }
    }
}
