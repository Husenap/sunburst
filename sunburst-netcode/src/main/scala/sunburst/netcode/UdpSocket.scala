package sunburst.netcode

import java.io.ObjectOutputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ByteArrayInputStream
import java.nio.channels.DatagramChannel
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.net.StandardProtocolFamily
import scala.collection.mutable.ArrayDeque

class UdpSocket[A <: Serializable]:
  private final val ProtocolId =
    Array[Byte](0xc0.toByte, 0xff.toByte, 0xee.toByte)

  private val baos         = ByteArrayOutputStream(1024)
  private val incomingData = new Array[Byte](1024)

  private var localSequence  = NetworkShort()
  private var remoteSequence = NetworkShort()

  private val seenPackets = ArrayDeque[NetworkShort]()

  private val channel = DatagramChannel.open(StandardProtocolFamily.INET)
  channel.configureBlocking(false)
  channel.socket.setReceiveBufferSize(incomingData.size)
  channel.socket.setSendBufferSize(incomingData.size)

  def bind(port: Int): Unit =
    channel.socket.bind(InetSocketAddress(port))

  def close(): Unit = channel.close()

  def send(destination: Address, message: A): Unit =
    baos.reset()

    baos.write(ProtocolId)

    val oos         = ObjectOutputStream(baos)
    oos.writeNetworkShort(localSequence)
    localSequence = localSequence.next
    oos.writeNetworkShort(remoteSequence)
    var ackBitfield = 0
    for seen <- seenPackets do
      val diff = remoteSequence - seen
      if (diff <= 32) then ackBitfield |= 1 << (diff - 1)
    oos.writeInt(ackBitfield)

    oos.writeObject(message)

    channel.send(
      ByteBuffer.wrap(baos.toByteArray),
      InetSocketAddress(destination.address, destination.port)
    )

  def receive(): Option[(Address, A)] =
    val sender = channel
      .receive(ByteBuffer.wrap(incomingData))
      .asInstanceOf[InetSocketAddress]

    if sender == null then None
    else
      val bais = ByteArrayInputStream(incomingData)

      if !(bais.readNBytes(ProtocolId.length) sameElements ProtocolId) then
        println(s"ignoring packet")
        None
      else
        val ois = ObjectInputStream(bais)

        val sequence    = ois.readNetworkShort()
        val ack         = ois.readNetworkShort()
        val ackBitfield = ois.readInt()

        if sequence > remoteSequence then
          seenPackets.append(remoteSequence)
          while seenPackets.length > 32 do seenPackets.removeHead()
          remoteSequence = sequence

        println(s"ACK : $ack")
        println(
          s"ACKs: ${("0" * 32 + ackBitfield.toBinaryString).takeRight(32)}"
        )
        val acks = (for
          i <- 1 to 32
          if (ackBitfield & (1 << (i - 1))) != 0
        yield sequence.value - i).toVector
        println(
          s"ACKs: $acks"
        )

        val message = ois.readObject().asInstanceOf[A]
        val from    = Address(sender.getAddress, sender.getPort)
        Some(from -> message)
