package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*

class VectorSpec extends AnyWordSpec with should.Matchers:
  "A Vector" should {

    "be commutatively additive" in {
      val v1 = Vector(1, 2, 3)
      val v2 = Vector(4, 5, 6)

      v1 + v2 shouldBe v2 + v1
    }

    "be subtractive" in {
      val v1 = Vector(1, 2, 3)
      val v2 = Vector(4, 5, 6)

      v1 - v2 shouldBe Vector(-3)
      v2 - v1 shouldBe Vector(3)
    }

    "be scalable by a constant factor" in {
      val v1 = Vector(1, 2, 3)

      v1 * 5 shouldBe Vector(5, 10, 15)
    }

    "be divisible by a constant factor" in {
      val v1 = Vector(1, 2, 3)

      v1 / 5 shouldBe Vector(0.2f, 0.4f, 0.6f)
      intercept[java.lang.ArithmeticException] {
        v1 / 0
      }
    }

    "be able to calculate a dot product" in {
      Vector(3, 4, 0).dot(Vector(3, 4, 0)) shouldBe 25
      Vector(3, 4, 0).dot(Vector(1, 2, 3)) shouldBe 11
    }

    "have a length" in {
      Vector(3, 4, 0).length shouldBe 5
      Vector(1, 2, 3).length shouldBe 3.74f +- 0.01f
    }

  }
