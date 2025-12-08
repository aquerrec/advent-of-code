@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTesterWithAdditionalOptions
import ExpectedValues
import FileReader

class Day08Test :
    AdventOfCodeTesterWithAdditionalOptions<Sequence<String>, Long, Int>(
        year = 2025,
        day = 8,
        fileReader = FileReader.SequenceReader,
        solver = Day08Solver,
        additionalOptions =
            AdditionalOptions(
                samplePart1 = 10,
                puzzlePart1 = 1000,
                samplePart2 = 0,
                puzzlePart2 = 0,
            ),
        expectedValues =
            ExpectedValues(
                samplePart1 = 40L,
                puzzlePart1 = 72_150L,
                samplePart2 = 25_272L,
                puzzlePart2 = 3_926_518_899L,
            ),
    )
