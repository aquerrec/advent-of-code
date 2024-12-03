package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readSequence

class Day02Test {
    private val sampleInput = readSequence("2024/day02/sample.txt")
    private val puzzleInput = readSequence("2024/day02/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay02Part1() shouldBe 2
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay02Part1() shouldBe 516
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the sample input`() {
            sampleInput.solveDay02Part2() shouldBe 4
        }

        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay02Part2() shouldBe 561
        }
    }

    @Test
    fun `should return all possibilities removing one level`() {
        listOf(1, 2, 3, 4, 5).allPossibilitiesRemovingOneLevel() shouldBe
            listOf(
                listOf(2, 3, 4, 5),
                listOf(1, 3, 4, 5),
                listOf(1, 2, 4, 5),
                listOf(1, 2, 3, 5),
                listOf(1, 2, 3, 4),
            )
    }
}
