package aoc2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.InputReader.Companion.readSequence

class Day03Test {
    private val sampleInput = readSequence("2024/day03/sample.txt")
    private val puzzleInput = readSequence("2024/day03/input.txt")

    @Nested
    inner class PartOne {
        @Test
        fun `should solve part 1 for the sample input`() {
            sampleInput.solveDay03Part1() shouldBe 161
        }

        @Test
        fun `should solve part 1 for the puzzle input`() {
            puzzleInput.solveDay03Part1() shouldBe 170_778_545
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should solve part 2 for the puzzle input`() {
            puzzleInput.solveDay03Part2() shouldBe 82_868_252
        }
    }

    @Test
    fun `should extract mul instructions`() {
        "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
            .extractMulInstructions()
            .toList() shouldBe
            listOf(
                "mul(2,4)",
                "mul(5,5)",
                "mul(11,8)",
                "mul(8,5)",
            )
    }

    @Test
    fun `should extract mul and do and dont instructions`() {
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
            .extractDoDontMulInstructions()
            .toList() shouldBe
            listOf(
                "mul(2,4)",
                "don't()",
                "mul(5,5)",
                "mul(11,8)",
                "do()",
                "mul(8,5)",
            )
    }

    @Test
    fun `should do multiplication`() {
        "mul(2,4)".doMul() shouldBe 8
        "mul(11,8)".doMul() shouldBe 88
    }
}
