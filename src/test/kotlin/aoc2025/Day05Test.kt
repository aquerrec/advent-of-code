@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day05Test :
    AdventOfCodeTester<Sequence<String>, Long>(
        year = 2025,
        day = 5,
        fileReader = FileReader.SequenceReader,
        solver = Day05Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 3,
                puzzlePart1 = 640,
                samplePart2 = 14,
                puzzlePart2 = 365_804_144_481_581L,
            ),
    )
