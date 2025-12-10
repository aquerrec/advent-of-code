@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day10Test :
    AdventOfCodeTester<Sequence<String>, Long>(
        year = 2025,
        day = 10,
        fileReader = FileReader.SequenceReader,
        solver = Day10Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 7,
                puzzlePart1 = 502,
                samplePart2 = 0,
                puzzlePart2 = 0,
            ),
    )
