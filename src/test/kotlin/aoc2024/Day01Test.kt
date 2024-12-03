package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readSequence

class Day01Test {
    private val sampleInput = readSequence("2024/day01/sample.txt")
    private val puzzleInput = readSequence("2024/day01/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay01Part1() shouldBe 11
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay01Part1() shouldBe 2_264_607
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay01Part2() shouldBe 31
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay01Part2() shouldBe 19_457_120
        }
    }
}
