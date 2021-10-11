package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*

class Vec4Spec extends AnyWordSpec with should.Matchers:
  "A Vec4" should {

    "be commutatively additive" in {
      val v1 = Vec4(1, 2, 3, 4)
      val v2 = Vec4(4, 5, 6, 7)

      v1 + v2 shouldBe v2 + v1
      v1 + 5 shouldBe Vec4(6, 7, 8, 9)
      (-2) + v2 shouldBe Vec4(2, 3, 4, 5)
    }

    "be subtractive" in {
      val v1 = Vec4(1, 2, 3, 4)
      val v2 = Vec4(4, 5, 6, 7)

      v1 - v2 shouldBe Vec4(-3)
      v2 - v1 shouldBe Vec4(3)
      v1 - 5 shouldBe Vec4(-4, -3, -2, -1)
      v2 - (-5) shouldBe Vec4(9, 10, 11, 12)
      (-5) - v2 shouldBe Vec4(-9, -10, -11, -12)
    }

    "be negatable" in {
      -Vec4(1, 2, 3, 4) shouldBe Vec4(-1, -2, -3, -4)
      -Vec4(-1, 2, -3, 4) shouldBe Vec4(1, -2, 3, -4)
    }

    "be multipliable by a scalar" in {
      val v1 = Vec4(1, 2, 3, 4)

      v1 * 5 shouldBe Vec4(5, 10, 15, 20)
      3 * v1 shouldBe Vec4(3, 6, 9, 12)
    }

    "be divisible by a scalar" in {
      val v1 = Vec4(1, 2, 3, 4)

      v1 / 5 shouldBe Vec4(0.2f, 0.4f, 0.6f, 0.8f)
      intercept[java.lang.IllegalArgumentException] {
        v1 / 0
      }
    }

    "be able to calculate a dot product" in {
      Vec4(2, 1, 4, 3) dot Vec4(3, 4, 1, 2) shouldBe 20
    }

    "have a magnitude" in {
      Vec4(0, 0, 3, 4).magnitude shouldBe 5
      Vec4(0, 0, 4, 3).magnitudeSquared shouldBe 25
      Vec4(1, 2, 3, 4).magnitude shouldBe 5.477f +- 0.01f
      Vec4.zero.magnitude shouldBe 0
    }

    "be normalizable" in {
      Vec4(42, 0, 0, 0).normalized shouldBe Vec4(1, 0, 0, 0)
      intercept[java.lang.IllegalArgumentException] {
        Vec4(0, 0, 0, 0).normalized
      }
    }

  }
