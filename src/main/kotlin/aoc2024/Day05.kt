package aoc2024

typealias PageOrderingRules = Map<Int, List<Int>>
typealias Update = List<Int>

private fun List<String>.getSections(): Pair<PageOrderingRules, List<Update>> =
    Pair(
        this[0]
            .lines()
            .map { it.split("|").map(String::toInt) }
            .groupBy({ it[0] }) { it[1] },
        this[1]
            .lines()
            .map { it.split(",").map(String::toInt) },
    )

private fun Update.isInTheRightOrder(pageOrderingRules: PageOrderingRules): Boolean =
    this.withIndex().all { (index, pageNumber) ->
        val pageRules = pageOrderingRules[pageNumber]
        val otherPages = this.drop(index + 1)
        otherPages.isEmpty() || (pageRules != null && pageRules.containsAll(otherPages))
    }

private fun Update.middlePageNumber(): Int = this[this.size / 2]

private fun Update.fixUpdateOrder(pageOrderingRules: PageOrderingRules): Update =
    this.sortedBy {
        pageOrderingRules[it]?.intersect(this)?.size ?: -1
    }

/**
 * Compute the sum of middle page numbers for the correctly-ordered updates.
 */
fun List<String>.solveDay05Part1(): Int {
    val (pageOrderingRules, updates) = this.getSections()

    return updates
        .filter { it.isInTheRightOrder(pageOrderingRules) }
        .sumOf { it.middlePageNumber() }
}

/**
 * Compute the sum of middle page numbers after correctly ordering the incorrectly-ordered updates.
 */
fun List<String>.solveDay05Part2(): Int {
    val (pageOrderingRules, updates) = this.getSections()

    return updates
        .filter { !it.isInTheRightOrder(pageOrderingRules) }
        .map { it.fixUpdateOrder(pageOrderingRules) }
        .sumOf { it.middlePageNumber() }
}
