package utils

/**
 * Compute the greatest common divisor of two numbers.
 */
fun gcd(
    first: Long,
    second: Long,
): Long {
    var a = first
    var b = second
    while (b > 0) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

/**
 * Compute the least common multiple of two numbers.
 */
fun lcm(
    a: Long,
    b: Long,
) = a * (b / gcd(a, b))
