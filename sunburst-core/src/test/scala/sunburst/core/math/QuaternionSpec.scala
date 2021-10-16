package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*

class QuaternionSpec extends AnyWordSpec with should.Matchers:
  "A Quaternion" should {

    "be commutatively additive" in {
      val q1 = Quaternion(1, 2, 3, 4)
      val q2 = Quaternion(5, 6, 7, 8)

      q1 + q2 shouldBe q2 + q1
      q1 + q2 shouldBe Quaternion(6, 8, 10, 12)
    }

    "be subtractive" in {
      val q1 = Quaternion(1, 2, 3, 4)
      val q2 = Quaternion(5, 6, 7, 8)

      q1 - q2 shouldBe Quaternion(-4, Vec3(-4))
      q2 - q1 shouldBe Quaternion(4, Vec3(4))
    }

    "be associatively multipliable by a quaternion" in {
      val q1 = Quaternion(1, 2, 3, 4)
      val q2 = Quaternion(5, 6, 7, 8)
      val q3 = Quaternion(9, 10, 11, 12)

      q1 * q2 shouldBe Quaternion(-60, 12, 30, 24)
      q2 * q1 shouldBe Quaternion(-60, 20, 14, 32)
      (q1 * q2) * q3 shouldBe q1 * (q2 * q3)
    }

    "be divisible by a quaternion" in {
      val q1 = Quaternion(1, 2, 3, 4)
      val q2 = Quaternion(5, 6, 7, 8)

      ((q1 / q2) - Quaternion(
        0.402f,
        0.046f,
        0.0f,
        0.092f
      )).magnitude shouldBe 0.0f +- 0.001f
    }

    "be multipliable by a scalar" in {
      Quaternion(1, 2, 3, 4) * 2.0f shouldBe Quaternion(2, 4, 6, 8)
    }

    "be divisible by a scalar" in {
      val q1 = Quaternion(2, 3, 4, 5)

      q1 / 2.0f shouldBe Quaternion(1.0f, 1.5f, 2.0f, 2.5f)
    }

    "have an inverse" in {
      (Quaternion(1, 2, 3, 4).inverse - Quaternion(
        0.033f,
        -0.067f,
        -0.1f,
        -0.133f
      )).magnitude shouldBe 0.0f +- 0.001f
      intercept[java.lang.AssertionError] {
        Quaternion(0, Vec3.Zero).inverse
      }
    }

    "be convertible into a rotation matrix" in {
      val rm = Quaternion
        .fromAxisAndAngle(Vec3(1, 2, 3), math.Pi.toFloat / 3)
        .normalized
        .rotationMatrix

      val epsilon = rm -
        Mat4(
          Vec4(0.536f, -0.623f, 0.57f, 0),
          Vec4(0.766f, 0.643f, -0.017f, 0),
          Vec4(-0.356, 0.446f, 0.821f, 0),
          Vec4(0, 0, 0, 1)
        )
      epsilon.x.magnitude shouldBe 0.0f +- 0.001f
      epsilon.y.magnitude shouldBe 0.0f +- 0.001f
      epsilon.z.magnitude shouldBe 0.0f +- 0.001f
      epsilon.w.magnitude shouldBe 0.0f +- 0.001f
    }

    "be able to rotate a vector around an axis" in {
      val v = Quaternion
        .rotateAroundAxis(Vec3(3, 7, 1), Vec3(1, 2, 3), math.Pi.toFloat / 3)

      (v - Vec3(-2.183, 6.78, 2.874)).magnitude shouldBe 0.0f +- 0.001f
    }

  }
