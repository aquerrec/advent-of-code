@file:Suppress("ktlint:standard:filename")

package aoc2025

import AdventOfCode
import AdventOfCodeTester2
import FileReader

class Day11Test :
    AdventOfCodeTester2<Sequence<String>, Long>(
        year = 2025,
        day = 11,
        fileReader = FileReader.SequenceReader,
        parts =
            AdventOfCode(
                samplePart1 =
                    AdventOfCode.SamplePart(
                        sampleFilename = "sample1.txt",
                        solve = Day11Solver::solvePart1,
                        expectedValue = 5L,
                    ),
                puzzlePart1 =
                    AdventOfCode.PuzzlePart(
                        solve = Day11Solver::solvePart1,
                        expectedValue = 534L,
                    ),
                samplePart2 =
                    AdventOfCode.SamplePart(
                        sampleFilename = "sample2.txt",
                        solve = Day11Solver::solvePart2,
                        expectedValue = 2L,
                    ),
                puzzlePart2 =
                    AdventOfCode.PuzzlePart(
                        solve = Day11Solver::solvePart2,
                        expectedValue = 499_645_520_864_100L,
                    ),
            ),
    )
