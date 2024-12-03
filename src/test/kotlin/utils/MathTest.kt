package utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MathTest {
    @Test
    fun `should compute the greatest common divisor of two numbers`() {
        gcd(10, 5) shouldBe 5
        gcd(10, 3) shouldBe 1
        gcd(112, 30) shouldBe 2
        gcd(10, 0) shouldBe 10
        gcd(0, 10) shouldBe 10
    }

    @Test
    fun `should compute the least common multiple of two numbers`() {
        lcm(10, 5) shouldBe 10
        lcm(10, 3) shouldBe 30
        lcm(112, 30) shouldBe 1680
        lcm(10, 0) shouldBe 0
        lcm(0, 10) shouldBe 0
    }
}
