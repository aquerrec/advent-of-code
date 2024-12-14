package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readInputAsText

class Day13Test {
    private val sampleInput = readInputAsText("2024/day13/sample.txt")
    private val puzzleInput = readInputAsText("2024/day13/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay13Part1() shouldBe 480
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay13Part1() shouldBe 39748
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay13Part2() shouldBe 875_318_608_908
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay13Part2() shouldBe 74_478_585_072_604
        }
    }
}
