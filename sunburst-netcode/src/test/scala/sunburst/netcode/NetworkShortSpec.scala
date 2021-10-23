package sunburst.netcode

package sunburst.core.math

import org.scalatest.*
import wordspec.*
import matchers.*
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

class NetworkShortSpec extends AnyWordSpec with should.Matchers:
  "A NetworkShort" should {
    "be comparable" in {
      NetworkShort(1) > NetworkShort(0) shouldBe true
      NetworkShort(0) > NetworkShort(1) shouldBe false

      NetworkShort(0) > NetworkShort(65535) shouldBe true
      NetworkShort(65535) > NetworkShort(0) shouldBe false

      NetworkShort(1) > NetworkShort(65535) shouldBe true

      NetworkShort(32768) > NetworkShort(32767) shouldBe true
      NetworkShort(32769) > NetworkShort(32768) shouldBe true
      NetworkShort(32770) > NetworkShort(32769) shouldBe true

      NetworkShort(32766) > NetworkShort(65535) shouldBe true
      NetworkShort(32767) > NetworkShort(65535) shouldBe false
      NetworkShort(0) > NetworkShort(32768) shouldBe false

      NetworkShort(0) > NetworkShort(32769) shouldBe true

      intercept[java.lang.IllegalArgumentException] {
        NetworkShort(65536) > NetworkShort(0) shouldBe true
      }
    }

    "be incrementable" in {
      var s = NetworkShort(65534)
      s.value shouldBe 65534
      s = s.next
      s.value shouldBe 65535
      s = s.next
      s.value shouldBe 0
      s > NetworkShort(65534) shouldBe true
    }

    "be serializable" in {
      val test = (i: Int) =>
        val baos = ByteArrayOutputStream()
        val oos  = ObjectOutputStream(baos)
        oos.writeNetworkShort(NetworkShort(i))
        oos.flush()

        val ois = ObjectInputStream(ByteArrayInputStream(baos.toByteArray))
        ois.readNetworkShort() shouldBe NetworkShort(i)

      test(0)
      test(1)

      test(32766)
      test(32767)
      test(32768)
      test(32769)

      test(65534)
      test(65535)
    }

    "be subtractive" in {
      NetworkShort(5) - NetworkShort(0) shouldBe 5
      NetworkShort(32768) - NetworkShort(32765) shouldBe 3
      NetworkShort(0) - NetworkShort(65535) shouldBe 1
      NetworkShort(3) - NetworkShort(65532) shouldBe 7
    }
  }
