package aoc2024

import utils.Graph
import utils.Matrix
import utils.Vertex

private fun Matrix<Int>.allTrailheadsVertices() = this.findAllByValueMatching { it == 0 }.map { Vertex(it) }

private fun Matrix<Int>.allTrailEndsVertices() = this.findAllByValueMatching { it == 9 }.map { Vertex(it) }

private fun hikingTrailConstraint(
    p1: Matrix.Cell<Int>,
    p2: Matrix.Cell<Int>,
) = p1.value + 1 == p2.value

/**
 * Compute the sum of the scores of all trailheads on your topographic map.
 */
fun Matrix<Int>.solveDay10Part1(): Int {
    val allTrailheads = this.allTrailheadsVertices()
    val allTrailEnds = this.allTrailEndsVertices()
    val graph = Graph.fromMatrix(this, withNeighborsConstraint = ::hikingTrailConstraint).also { println(it) }
    return allTrailheads.sumOf { trailhead ->
        allTrailEnds.count { end -> graph.numberOfPaths(trailhead, end) > 0 }
    }
}

/**
 * Compute the sum of the ratings of all trailheads.
 */
fun Matrix<Int>.solveDay10Part2(): Int {
    val allTrailheads = this.allTrailheadsVertices()
    val allTrailEnds = this.allTrailEndsVertices()
    val graph = Graph.fromMatrix(this, withNeighborsConstraint = ::hikingTrailConstraint)
    return allTrailheads.sumOf { trailhead ->
        allTrailEnds.sumOf { end -> graph.numberOfPaths(trailhead, end).toInt() }
    }
}
