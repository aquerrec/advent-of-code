package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readSequence

class Day04Test {
    private val sampleInput = readSequence("2024/day04/sample.txt")
    private val puzzleInput = readSequence("2024/day04/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay04Part1() shouldBe 18
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay04Part1() shouldBe 2551
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay04Part2() shouldBe 9
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay04Part2() shouldBe 1985
        }
    }
}
