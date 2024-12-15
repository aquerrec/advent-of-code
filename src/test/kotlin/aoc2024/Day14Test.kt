package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Boundaries
import utils.InputReader.Companion.readLines

class Day14Test {
    private val sampleInput = readLines("2024/day14/sample.txt")
    private val puzzleInput = readLines("2024/day14/input.txt")

    private val sampleBoundaries = Boundaries.ofSize(11, 7)
    private val puzzleBoundaries = Boundaries.ofSize(101, 103)

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay14Part1(sampleBoundaries) shouldBe 12
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay14Part1(puzzleBoundaries) shouldBe 230686500
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay14Part2(puzzleBoundaries) shouldBe 7672
        }
    }
}
