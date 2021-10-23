package sunburst.netcode

import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import sunburst.netcode.NetworkShort

case class NetworkShort private (val value: Int = 0):
  def -(other: NetworkShort): Int =
    ((value - other.value) + 65536) % 65536

  def >(other: NetworkShort): Boolean =
    ((value > other.value) && (value - other.value <= 32768)) ||
      ((value < other.value) && (other.value - value > 32768))

  def next: NetworkShort =
    NetworkShort((value + 1) % 65536)

object NetworkShort:
  def apply(value: Int = 0): NetworkShort =
    require(value >= 0 && value < 65536)
    new NetworkShort(value)

extension (ois: ObjectInputStream)
  def readNetworkShort(): NetworkShort =
    val i = ois.readShort().toInt
    if i < 0 then NetworkShort(i + 65536)
    else NetworkShort(i)

extension (oos: ObjectOutputStream)
  def writeNetworkShort(ns: NetworkShort) =
    oos.writeShort(ns.value)
