@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day06Test :
    AdventOfCodeTester<Sequence<String>, Long>(
        year = 2025,
        day = 6,
        fileReader = FileReader.SequenceReader,
        solver = Day06Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 4_277_556L,
                puzzlePart1 = 4_878_670_269_096L,
                samplePart2 = 3_263_827L,
                puzzlePart2 = 8_674_740_488_592L,
            ),
    )
