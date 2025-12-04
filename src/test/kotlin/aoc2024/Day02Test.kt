package aoc2024

import AdventOfCodeTester
import ExpectedValues
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test :
    AdventOfCodeTester<Sequence<String>, Int>(
        year = 2024,
        day = 2,
        fileReader = FileReader.SequenceReader,
        solver = Day02Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 2,
                puzzlePart1 = 516,
                samplePart2 = 4,
                puzzlePart2 = 561,
            ),
    ) {
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
