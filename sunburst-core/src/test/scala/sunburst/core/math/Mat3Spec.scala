package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*

class Mat3Spec extends AnyWordSpec with should.Matchers:
  "A Mat3" should {
    "have an identity matrix" in {
      val mat = Mat3.identity
      mat.x shouldBe Vec3(1, 0, 0)
      mat.y shouldBe Vec3(0, 1, 0)
      mat.z shouldBe Vec3(0, 0, 1)
    }

    "be commutatively additive" in {
      val m1 = Mat3(
        Vec3(3, 4, 9),
        Vec3(6, 8, 6),
        Vec3(7, 3, 4)
      )
      val m2 = Mat3(
        Vec3(1, 6, 7),
        Vec3(6, 4, 2),
        Vec3(4, 1, 5)
      )

      m1 + m2 shouldBe m2 + m1
      m1 + m2 shouldBe Mat3(
        Vec3(4, 10, 16),
        Vec3(12, 12, 8),
        Vec3(11, 4, 9)
      )
    }

    "be subtractive" in {
      val m1 = Mat3(
        Vec3(3, 4, 9),
        Vec3(6, 8, 6),
        Vec3(7, 3, 4)
      )
      val m2 = Mat3(
        Vec3(1, 6, 7),
        Vec3(6, 4, 2),
        Vec3(4, 1, 5)
      )

      m1 - m2 shouldBe Mat3(
        Vec3(2, -2, 2),
        Vec3(0, 4, 4),
        Vec3(3, 2, -1)
      )
      m2 - m1 shouldBe Mat3(
        Vec3(-2, 2, -2),
        Vec3(0, -4, -4),
        Vec3(-3, -2, 1)
      )
    }

    "be able to perform matrix multiplication" in {
      val m1 = Mat3(-1, 2, 0, -4, 3, -2, 1, 4, 5)
      val m2 = Mat3(6, 7, 8, 2, 9, 3, -3, -2, 4)

      m1 * m2 shouldBe Mat3(-2, 11, -2, -12, 3, -31, -1, 33, 40)
      m2 * m1 shouldBe Mat3(-26, 65, 26, -35, 43, -3, 15, 4, 24)
    }

    "be able to perfom matrix-vector multiplication" in {
      val m1 = Mat3(-1, 2, 0, -4, 3, -2, 1, 4, 5)
      val m2 = Mat3(6, 7, 8, 2, 9, 3, -3, -2, 4)
      val v  = Vec3(7, 4, 1)

      m1 * v shouldBe Vec3(1, -18, 28)
      m2 * v shouldBe Vec3(78, 53, -25)
    }
  }
