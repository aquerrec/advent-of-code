package aoc2024

import utils.Matrix
import utils.Point

private fun Matrix<Char>.findAllAntennasAndAntinodesPositions(): List<Matrix.Cell<Char>> = findAllByValueMatching { it != '.' }

private fun Matrix<Char>.findAllAntennasPositionsGroupedByFrequency(): Map<Char, List<Point>> =
    findAllByValueMatching { it != '.' }.groupBy({ it.value }) { it.coordinate }

private fun Matrix<Char>.getAllAntinodesPositions(withResonantHarmonics: Boolean = false): Set<Point> =
    this
        .findAllAntennasPositionsGroupedByFrequency()
        .flatMap { (_, antennasPositions) ->
            antennasPositions.flatMapIndexed { index1, point1 ->
                (index1 + 1 until antennasPositions.size).flatMap { index2 ->
                    val point2 = antennasPositions[index2]
                    val diff = point2 - point1

                    if (withResonantHarmonics) {
                        generateSequence(point1) { it - diff }
                            .takeWhile { it in this }
                            .drop(1) +
                            generateSequence(point2) { it + diff }
                                .takeWhile { it in this }
                                .drop(1)
                    } else {
                        sequenceOf(
                            (point1 - diff).takeIf { it in this },
                            (point2 + diff).takeIf { it in this },
                        ).filterNotNull()
                    }
                }
            }
        }.toSet()

/**
 * Count unique locations within the bounds of the map containing an antinode.
 */
fun Matrix<Char>.solveDay08Part1(): Int = this.getAllAntinodesPositions().size

/**
 * Count unique locations within the bounds of the map containing an antinode
 * taking into account the effects of resonant harmonics.
 */
fun Matrix<Char>.solveDay08Part2(): Int =
    this
        .copyWithCellsValue(
            points = getAllAntinodesPositions(withResonantHarmonics = true),
            value = '#',
        ).findAllAntennasAndAntinodesPositions()
        .size
