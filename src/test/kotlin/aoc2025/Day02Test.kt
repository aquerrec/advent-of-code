@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCodeTester
import ExpectedValues
import FileReader

class Day02Test :
    AdventOfCodeTester<String, Long>(
        year = 2025,
        day = 2,
        fileReader = FileReader.FirstLineReader,
        solver = Day02Solver,
        expectedValues = ExpectedValues(1_227_775_554L, 28_146_997_880L, 4_174_379_265L, 40_028_128_307L),
    )
