@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day01Test :
    AdventOfCodeTester<Sequence<String>, Int>(
        year = 2025,
        day = 1,
        fileReader = FileReader.SequenceReader,
        solver = Day01Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 3,
                puzzlePart1 = 1036,
                samplePart2 = 6,
                puzzlePart2 = 6228,
            ),
    )
