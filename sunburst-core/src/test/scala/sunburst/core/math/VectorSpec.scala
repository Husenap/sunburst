package sunburst.core.math

import org.scalatest.*
import flatspec.*
import matchers.*

class VectorSpec extends AnyFlatSpec with should.Matchers:
  "A Vector" should "be commutatively additive" in {
    val v1 = Vector(1, 2, 3)
    val v2 = Vector(4, 5, 6)

    val res1 = v1 + v2
    val res2 = v2 + v1
    res1 shouldBe res2
  }
  it should "be scalable with a constant factor" in {
    val v1 = Vector(1, 2, 3)
    val v2 = v1 * 5

    v2 shouldBe Vector(5, 10, 15)
  }
