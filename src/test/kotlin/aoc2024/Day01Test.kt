@file:Suppress("ktlint:standard:filename")

package aoc2024

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day01Test :
    AdventOfCodeTester<Sequence<String>, Int>(
        year = 2024,
        day = 1,
        fileReader = FileReader.SequenceReader,
        solver = Day01Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 11,
                puzzlePart1 = 2_264_607,
                samplePart2 = 31,
                puzzlePart2 = 19_457_120,
            ),
    )
