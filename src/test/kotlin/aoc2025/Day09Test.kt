@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day09Test :
    AdventOfCodeTester<Sequence<String>, Long>(
        year = 2025,
        day = 9,
        fileReader = FileReader.SequenceReader,
        solver = Day09Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 50L,
                puzzlePart1 = 4_741_848_414L,
                samplePart2 = 24,
                puzzlePart2 = 1_508_918_480L,
            ),
    )
