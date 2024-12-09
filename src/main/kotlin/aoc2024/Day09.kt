package aoc2024

private const val FREE_SPACE = "."

private fun String.toIndividualBlocks(): List<String> =
    flatMapIndexed { index, char ->
        val num = char.digitToInt()
        val block = if (index % 2 == 0) (index / 2).toString() else FREE_SPACE
        List(num) { block }
    }

private fun List<String>.moveFileBlocks(): List<String> {
    val copy = this.toMutableList()
    var fileBlockIndex = this.lastIndex

    for (i in copy.indices) {
        if (i > fileBlockIndex) {
            break
        }
        if (copy[i] == FREE_SPACE) {
            while (copy[fileBlockIndex] == FREE_SPACE) {
                fileBlockIndex--
            }
            copy[i] = copy[fileBlockIndex]
            copy[fileBlockIndex] = FREE_SPACE
            fileBlockIndex--
        }
    }

    return copy
}

private fun List<String>.firstAvailableSpaceIndexFor(
    fileSize: Int,
    indexMax: Int,
): Int {
    var start = 0
    val max = (indexMax - 1).coerceAtMost(this.size - fileSize)
    while (start <= max) {
        if (this.subList(start, start + fileSize).all { it == FREE_SPACE }) {
            return start
        }
        start++
    }
    return -1
}

private fun List<String>.moveContiguousFileBlocks(): List<String> {
    val copy = this.toMutableList()
    var fileUpperIndex = copy.lastIndex

    while (fileUpperIndex > 0) {
        val fileId = copy[fileUpperIndex]
        if (fileId == FREE_SPACE) {
            fileUpperIndex--
            continue
        }
        val fileLowerIndex = copy.indexOf(fileId)
        val fileSize = fileUpperIndex - fileLowerIndex + 1

        copy.firstAvailableSpaceIndexFor(fileSize, fileLowerIndex).let {
            if (it != -1) {
                for (i in 0 until fileSize) {
                    copy[it + i] = fileId
                    copy[fileLowerIndex + i] = FREE_SPACE
                }
            }
        }
        fileUpperIndex = fileLowerIndex - 1
    }

    return copy
}

private fun List<String>.computeChecksum(): Long =
    this
        .mapIndexed { index, fileId ->
            if (fileId == FREE_SPACE) 0L else index * fileId.toLong()
        }.sum()

/**
 * Compute the filesystem checksum after compaction with fragmentation.
 */
fun String.solveDay09Part1(): Long = this.toIndividualBlocks().moveFileBlocks().computeChecksum()

/**
 * Compute the filesystem checksum after compaction without fragmentation.
 */
fun String.solveDay09Part2(): Long = this.toIndividualBlocks().moveContiguousFileBlocks().computeChecksum()
