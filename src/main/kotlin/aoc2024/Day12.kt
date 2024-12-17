package aoc2024

import utils.Direction
import utils.Matrix
import utils.Point

typealias Region = Collection<Point>

private fun Matrix<Char>.allRegions(): List<Region> = this.groupByValue().values.flatMap { it.splitIntoRegions() }

private fun Region.splitIntoRegions(): List<Region> =
    this.fold(mutableListOf()) { regions, point ->
        regions.apply {
            val foundRegions =
                point.neighbors().flatMap { neighbor -> regions.filter { it.contains(neighbor) } }
            regions.removeAll(foundRegions)
            regions.add(foundRegions.flatten().toSet() + point)
        }
    }

// perimeter = sum of the 4 sides for each point minus common sides with neighbors
private fun Region.price(): Int = this.sumOf { point -> 4 - point.neighbors().count { it in this } } * this.size

private fun Region.bulkPrice(): Int =
    Direction.udlrEntries().sumOf { direction ->
        // get all distinct regions that are neighbors of the current region
        this.filter { point -> point.move(direction) !in this }.splitIntoRegions().size
    } * this.size

/**
 * Compute the total price of fencing all regions
 */
fun Matrix<Char>.solveDay12Part1(): Int = this.allRegions().sumOf { it.price() }

/**
 * Compute the total bulk price of fencing all regions
 */
fun Matrix<Char>.solveDay12Part2(): Int = this.allRegions().sumOf { it.bulkPrice() }
