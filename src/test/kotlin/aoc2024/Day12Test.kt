package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readMatrix

class Day12Test {
    private val sampleInput = readMatrix("2024/day12/sample.txt")
    private val puzzleInput = readMatrix("2024/day12/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay12Part1() shouldBe 1930
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay12Part1() shouldBe 1546338
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay12Part2() shouldBe 1206
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay12Part2() shouldBe 978590
        }
    }
}
