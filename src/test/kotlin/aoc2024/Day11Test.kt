package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readFirstLine

class Day11Test {
    private val sampleInput = readFirstLine("2024/day11/sample.txt")
    private val puzzleInput = readFirstLine("2024/day11/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay11Part1() shouldBe 55_312
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay11Part1() shouldBe 189_167
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay11Part2() shouldBe 65_601_038_650_482
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay11Part2() shouldBe 225_253_278_506_288
        }
    }
}
