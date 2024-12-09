package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readFirstLine

class Day09Test {
    private val sampleInput = readFirstLine("2024/day09/sample.txt")
    private val puzzleInput = readFirstLine("2024/day09/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay09Part1() shouldBe 1928
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay09Part1() shouldBe 6_299_243_228_569
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay09Part2() shouldBe 2858
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay09Part2() shouldBe 6_326_952_672_104
        }
    }
}
