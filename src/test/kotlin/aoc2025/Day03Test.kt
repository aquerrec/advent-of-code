@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day03Test :
    AdventOfCodeTester<Sequence<String>, ULong>(
        year = 2025,
        day = 3,
        fileReader = FileReader.SequenceReader,
        solver = Day03Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 357u,
                puzzlePart1 = 17321u,
                samplePart2 = 3_121_910_778_619u,
                puzzlePart2 = 171_989_894_144_198u,
            ),
    )
