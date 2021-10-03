package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*

class Vec3Spec extends AnyWordSpec with should.Matchers:
  "A Vec3" should {

    "be commutatively additive" in {
      val v1 = Vec3(1, 2, 3)
      val v2 = Vec3(4, 5, 6)

      v1 + v2 shouldBe v2 + v1
      v1 + 5 shouldBe Vec3(6, 7, 8)
      (-2) + v2 shouldBe Vec3(2, 3, 4)
    }

    "be subtractive" in {
      val v1 = Vec3(1, 2, 3)
      val v2 = Vec3(4, 5, 6)

      v1 - v2 shouldBe Vec3(-3)
      v2 - v1 shouldBe Vec3(3)
      v1 - 5 shouldBe Vec3(-4, -3, -2)
      v2 - (-5) shouldBe Vec3(9, 10, 11)
      (-5) - v2 shouldBe Vec3(-9, -10, -11)
    }

    "be negatable" in {
      -Vec3(1, 2, 3) shouldBe Vec3(-1, -2, -3)
      -Vec3(-1, 2, -3) shouldBe Vec3(1, -2, 3)
    }

    "be multipliable by a scalar" in {
      val v1 = Vec3(1, 2, 3)

      v1 * 5 shouldBe Vec3(5, 10, 15)
      3 * v1 shouldBe Vec3(3, 6, 9)
    }

    "be divisible by a scalar" in {
      val v1 = Vec3(1, 2, 3)

      v1 / 5 shouldBe Vec3(0.2f, 0.4f, 0.6f)
      intercept[java.lang.IllegalArgumentException] {
        v1 / 0
      }
    }

    "be able to calculate a dot product" in {
      Vec3(3, 4, 0) dot Vec3(3, 4, 0) shouldBe 25
      Vec3(3, 4, 0) dot Vec3(1, 2, 3) shouldBe 11
    }

    "have a magnitude" in {
      Vec3(3, 4, 0).magnitude shouldBe 5
      Vec3(3, 4, 0).magnitudeSquared shouldBe 25
      Vec3(1, 2, 3).magnitude shouldBe 3.74f +- 0.01f
      Vec3.zero.magnitude shouldBe 0
    }

    "be able to calculate a cross product" in {
      Vec3(1, 0, 0) cross Vec3(0, 1, 0) shouldBe Vec3(0, 0, 1)
      Vec3(2, 3, 4) cross Vec3(5, 6, 7) shouldBe Vec3(-3, 6, -3)
      Vec3(2, 3, 4) cross Vec3(2, 3, 4) shouldBe Vec3(0)
    }

    "be normalizable" in {
      Vec3(42, 0, 0).normalized shouldBe Vec3(1, 0, 0)
      intercept[java.lang.IllegalArgumentException] {
        Vec3(0, 0, 0).normalized
      }
    }

  }
