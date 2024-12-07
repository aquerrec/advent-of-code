package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readSequence

class Day07Test {
    private val sampleInput = readSequence("2024/day07/sample.txt")
    private val puzzleInput = readSequence("2024/day07/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay07Part1() shouldBe 3749
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay07Part1() shouldBe 7_579_994_664_753
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay07Part2() shouldBe 11387
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay07Part2() shouldBe 438_027_111_276_610
        }
    }
}
