@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader
import utils.Matrix

class Day07Test :
    AdventOfCodeTester<Matrix<Char>, Long>(
        year = 2025,
        day = 7,
        fileReader = FileReader.MatrixOfCharReader,
        solver = Day07Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 21,
                puzzlePart1 = 1585,
                samplePart2 = 40,
                puzzlePart2 = 16_716_444_407_407L,
            ),
    )
