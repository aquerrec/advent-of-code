package utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ExtensionsTest {
    @Test
    fun `should count the differences between two strings`() {
        "abcdefghijkl".countDifferencesWith("abcdefghijkl") shouldBe 0
        "abcdefghijkl".countDifferencesWith("abbbefghijkl") shouldBe 2
        "abcdefghijkl".countDifferencesWith("mnopqrstuvwx") shouldBe 12
    }

    @Test
    fun `should return the first digit of a string`() {
        "abc123".firstDigit() shouldBe '1'
        "abc 123".firstDigit() shouldBe '1'
    }

    @Test
    fun `should return the last digit of a string`() {
        "abc123def".lastDigit() shouldBe '3'
        "abc 123 def".lastDigit() shouldBe '3'
    }

    @Test
    fun `should split a string by space and filter out blank strings`() {
        "12 34".splitBySpace() shouldBe listOf("12", "34")
        "12     34".splitBySpace() shouldBe listOf("12", "34")
    }

    @Test
    fun `should split a string half`() {
        "123456".splitHalf() shouldBe Pair("123", "456")
    }

    @Test
    fun `should split a string to integers`() {
        "01234".splitToInt() shouldBe listOf(0, 1, 2, 3, 4)
    }

    @Test
    fun `should adds a nullable long to another one`() {
        1L + 2L shouldBe 3L
        1L + null shouldBe 1L
        null + 1L shouldBe 1L
        null + null shouldBe 0L
    }

    @Test
    fun `should a progression from an integer to another one`() {
        1 toward 5 shouldBe 1..5
        5 toward 1 shouldBe (5 downTo 1)
    }

    @Test
    fun `should pad a progression of integers to the desired size`() {
        1..5 padTo 10 shouldBe listOf(1, 2, 3, 4, 5, 5, 5, 5, 5, 5)
        (5 downTo 1) padTo 10 shouldBe listOf(5, 4, 3, 2, 1, 1, 1, 1, 1, 1)
    }

    @Test
    fun `should pad a list of integers to the desired size`() {
        listOf(1, 2, 3, 4, 5) padTo 10 shouldBe listOf(1, 2, 3, 4, 5, 5, 5, 5, 5, 5)
    }

    @Test
    fun `should repeat a list n times`() {
        listOf(1, 2, 3) repeat 3 shouldBe listOf(1, 2, 3, 1, 2, 3, 1, 2, 3)
    }

    @Test
    fun `should split a list into two parts`() {
        listOf(1, 2, 3, 4, 5) takeSplit 2 shouldBe Pair(listOf(1, 2), listOf(3, 4, 5))
    }

    @Test
    fun `should count the differences between two lists of strings`() {
        listOf("abc", "def", "ghi", "jkl").countDifferencesWith(listOf("abc", "def", "ghi", "jkl")) shouldBe 0
        listOf("abc", "def", "ghi", "jkl").countDifferencesWith(listOf("abb", "bef", "ghi", "jkl")) shouldBe 2
        listOf("abc", "def", "ghi", "jkl").countDifferencesWith(listOf("mno", "pqr", "stu", "vwx")) shouldBe 12
    }

    @Test
    fun `should find the line of reflection`() {
        listOf(
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#",
        ).findLineOfReflection() shouldBe 4
    }

    @Test
    fun `should transpose a list of strings`() {
        listOf(
            "ABC",
            "123",
            "DEF",
        ).transpose() shouldBe
            listOf(
                "A1D",
                "B2E",
                "C3F",
            )
    }
}
