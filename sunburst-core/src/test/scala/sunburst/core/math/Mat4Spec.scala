package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*

class Mat4Spec extends AnyWordSpec with should.Matchers:
  "A Mat4" should {
    "have an identity matrix" in {
      val mat = Mat4.Identity
      mat.x shouldBe Vec4(1, 0, 0, 0)
      mat.y shouldBe Vec4(0, 1, 0, 0)
      mat.z shouldBe Vec4(0, 0, 1, 0)
      mat.w shouldBe Vec4(0, 0, 0, 1)
    }

    "be commutatively additive" in {
      val m1 = Mat4(
        Vec4(3, 4, 9, 4),
        Vec4(6, 8, 6, 2),
        Vec4(7, 3, 4, 7),
        Vec4(2, 5, 1, 1)
      )
      val m2 = Mat4(
        Vec4(1, 6, 7, 0),
        Vec4(6, 4, 2, 7),
        Vec4(4, 1, 5, 9),
        Vec4(2, 5, 3, 1)
      )

      m1 + m2 shouldBe m2 + m1
      m1 + m2 shouldBe Mat4(
        Vec4(4, 10, 16, 4),
        Vec4(12, 12, 8, 9),
        Vec4(11, 4, 9, 16),
        Vec4(4, 10, 4, 2)
      )
    }

    "be subtractive" in {
      val m1 = Mat4(
        Vec4(3, 4, 9, 4),
        Vec4(6, 8, 6, 2),
        Vec4(7, 3, 4, 7),
        Vec4(2, 5, 1, 1)
      )
      val m2 = Mat4(
        Vec4(1, 6, 7, 0),
        Vec4(6, 4, 2, 7),
        Vec4(4, 1, 5, 9),
        Vec4(2, 5, 3, 1)
      )

      m1 - m2 shouldBe Mat4(
        Vec4(2, -2, 2, 4),
        Vec4(0, 4, 4, -5),
        Vec4(3, 2, -1, -2),
        Vec4(0, 0, -2, 0)
      )
    }

    "be able to perform matrix multiplication" in {
      val m1 = Mat4(
        Vec4(3, 4, 9, 4),
        Vec4(6, 8, 6, 2),
        Vec4(7, 3, 4, 7),
        Vec4(2, 5, 1, 1)
      )
      val m2 = Mat4(
        Vec4(1, 6, 7, 0),
        Vec4(6, 4, 2, 7),
        Vec4(4, 1, 5, 9),
        Vec4(2, 5, 3, 1)
      )

      m1 * m2 shouldBe Mat4(
        71, 63, 86, 113, 82, 84, 94, 112, 55, 93, 96, 64, 38, 38, 32, 45
      )
      m2 * m1 shouldBe Mat4(
        88, 73, 73, 65, 70, 97, 93, 53, 71, 84, 71, 62, 59, 62, 61, 40
      )
    }

    "be able to perfom matrix-vector multiplication" in {
      val m1 = Mat4(-1, 2, 0, -4, 3, -2, 1, 4, 5, 4, 10, 11, 6, 4, 7, 2)
      val m2 = Mat4(6, 7, 8, 2, 9, 3, -3, -2, 4, -4, 5, -7, 5, 6, 1, -9)
      val v  = Vec4(7, 4, 1, 5)

      m1 * v shouldBe Vec4(-19, 34, 116, 75)
      m2 * v shouldBe Vec4(88, 62, -18, 15)
    }

    "be transposable" in {
      Mat4(
        Vec4(-1, 2, 0, 7),
        Vec4(-4, 3, -2, 9),
        Vec4(1, 4, 5, 0),
        Vec4(-4, 8, 6, 2)
      ).transposed shouldBe Mat4(
        Vec4(-1, -4, 1, -4),
        Vec4(2, 3, 4, 8),
        Vec4(0, -2, 5, 6),
        Vec4(7, 9, 0, 2)
      )
    }

    "have a determinant" in {
      Mat4.Identity.determinant shouldBe 1
      Mat4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16).determinant shouldBe 0
      Mat4(2, 5, 1, 3, 4, 1, 7, 9, 6, 8, 3, 2, 7, 8, 1,
        4).determinant shouldBe 630
    }

    "have an inverse" in {
      Mat4.Identity.inverse shouldBe Mat4.Identity

      (Mat4(2, 5, 1, 3, 4, 1, 7, 9, 6, 8, 3, 2, 7, 8, 1, 4).inverse - Mat4(
        -0.3905, 0.0222, -0.0032, 0.2444, 0.2619, -0.0556, 0.0794, -0.1111,
        -0.0286, 0.0667, 0.2762, -0.2667, 0.1667, 0.0556, -0.2222, 0.1111
      )).toArray.sum shouldBe 0f +- 0.0001f

      intercept[java.lang.AssertionError] {
        Mat4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16).inverse
      }
    }
  }
