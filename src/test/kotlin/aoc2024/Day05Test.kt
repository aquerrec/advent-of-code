package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readInputAsText
import utils.splitByEmptyLine

class Day05Test {
    private val sampleInput = readInputAsText("2024/day05/sample.txt").splitByEmptyLine()
    private val puzzleInput = readInputAsText("2024/day05/input.txt").splitByEmptyLine()

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay05Part1() shouldBe 143
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay05Part1() shouldBe 5329
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay05Part2() shouldBe 123
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay05Part2() shouldBe 5833
        }
    }
}
