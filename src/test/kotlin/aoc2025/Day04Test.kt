@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader
import utils.Matrix

class Day04Test :
    AdventOfCodeTester<Matrix<Char>, Int>(
        year = 2025,
        day = 4,
        fileReader = FileReader.MatrixOfCharReader,
        solver = Day04Solver,
        expectedValues =
            ExpectedValues(
                samplePart1 = 13,
                puzzlePart1 = 1569,
                samplePart2 = 43,
                puzzlePart2 = 9280,
            ),
    )
