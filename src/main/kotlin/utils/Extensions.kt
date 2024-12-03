package utils

/**
 * Counts the number of differences between two strings.
 */
fun String.countDifferencesWith(other: String) = this.zip(other).count { it.first != it.second }

/**
 * Returns the first digit of a string.
 * Ex: "abc123" returns '1'
 */
fun String.firstDigit(): Char = this.first { it.isDigit() }

/**
 * Returns the last digit of a string.
 * Ex: "abc123def" returns '3'
 */
fun String.lastDigit(): Char = this.last { it.isDigit() }

/**
 * Split a string by space and filter out blank strings.
 * Ex: "12   34" becomes ["12", "34"]
 */
fun String.splitBySpace(): List<String> = split(" ").filter { it.isNotBlank() }

/**
 * Split a string into a list of integers.
 * Ex: "01234" becomes [0, 1, 2, 3, 4]
 */
fun String.splitToInt(): List<Int> = map(Character::getNumericValue)

/**
 * Adds the other nullable long value (0 if null) to this long value.
 */
operator fun Long.plus(other: Long?): Long = this + (other ?: 0)

/**
 * Adds the other nullable long value (0 if null) to this nullable long value.
 */
operator fun Long?.plus(other: Long?): Long = (this ?: 0L) + (other ?: 0L)

/**
 * Returns a progression from this Int to another Int.
 */
infix fun Int.toward(to: Int): IntProgression = IntProgression.fromClosedRange(this, to, 1.takeIf { this <= to } ?: -1)

/**
 * Pads a progression of Int to the desired size, using the final Int as the pad value
 */
infix fun IntProgression.padTo(newSize: Int): List<Int> = toList().padTo(newSize)

/**
 * Pads a list of anything to the desired size, using the final object as the pad object
 * Ex: listOf(1,2,3).padTo(10) becomes [1, 2, 3, 3, 3, 3, 3, 3, 3, 3]
 */
infix fun <T> List<T>.padTo(newSize: Int): List<T> = takeIf { size >= newSize } ?: plus(List(newSize - size) { last() })

/**
 * Repeats a list n times.
 */
infix fun <T> List<T>.repeat(n: Int): List<T> = List(this.size * n) { index -> this[index % this.size] }

/**
 * Splits a list into two, one with the first N elements the other with the remainder of the original list.
 */
infix fun <T> List<T>.takeSplit(n: Int): Pair<List<T>, List<T>> = take(n) to drop(n)

/**
 * Counts the number of differences between two lists of strings.
 */
fun List<String>.countDifferencesWith(other: List<String>): Int = this.zip(other) { l1, l2 -> l1.countDifferencesWith(l2) }.sum()

fun List<String>.findLineOfReflection(nbDiffAllowed: Int = 0): Int =
    (1 until this.size).firstOrNull { index ->
        val firstPart = this.take(index).asReversed()
        val secondPart = this.drop(index)
        firstPart.countDifferencesWith(secondPart) == nbDiffAllowed
    } ?: 0

fun List<String>.transpose(): List<String> =
    List(this.first().length) { col ->
        joinToString("") { it[col].toString() }
    }
