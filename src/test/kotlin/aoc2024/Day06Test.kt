package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readMatrix

class Day06Test {
    private val sampleInput = readMatrix("2024/day06/sample.txt")
    private val puzzleInput = readMatrix("2024/day06/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay06Part1() shouldBe 41
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay06Part1() shouldBe 4964
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay06Part2() shouldBe 6
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay06Part2() shouldBe 1740
        }
    }
}
