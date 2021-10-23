import sunburst.netcode.*
import java.net.InetAddress

val Port    = 13795
val PPS     = 1 / 5.0
val Timeout = 5.0

@main def server =
  val socket = UdpSocket[Message]()
  socket.bind(Port)

  var client: Option[Address] = None
  while true do
    println("Waiting for a client to connect...")
    while client.isEmpty do
      socket.receive().foreach((from, message) => client = Some(from))

    println(s"Established connection to ${client.get}")

    var t0          = System.nanoTime
    var accumulator = 0.0
    var t           = 0.0
    var i           = 0
    while accumulator < Timeout do
      val t1 = System.nanoTime
      val dt = (t1 - t0) / 1e9
      accumulator += dt
      t += dt
      t0 = t1

      while t > PPS do
        socket.send(client.get, Ping("-" * i))
        i = (i + 1) % 10
        t -= PPS

      while
        val packet = socket.receive()
        packet
          .filter((from, _) => from == client.get)
          .foreach((from, message) =>
            println(s"From $from: $message")
            accumulator = 0.0
          )
        packet.isDefined
      do ()

    println(s"Lost connection to ${client.get}")
    client = None

@main def client =
  val socket = UdpSocket[Message]()

  try
    val ip      = scala.io.StdIn.readLine("IP> ")
    val port    = scala.io.StdIn.readLine("PORT> ").toInt
    val address = InetAddress.getByName(ip)
    println(s"Host Name: ${address.getHostName()}")
    println(s"IP Address: ${address.getHostAddress()}")

    var t0          = System.nanoTime
    var accumulator = 0.0
    var t           = 0.0
    var i           = 0
    while accumulator < Timeout do
      val t1 = System.nanoTime
      val dt = (t1 - t0) / 1e9
      accumulator += dt
      t += dt
      t0 = t1

      while t > PPS do
        socket.send(Address(address, port), Ping("-" * i))
        i = (i + 1) % 10
        t -= PPS

      socket
        .receive()
        .foreach((from, message) =>
          println(s"From $from: $message")
          accumulator = 0.0
        )

    println("Lost connection to server!")

  catch case e: Exception => e.printStackTrace()
  finally socket.close()

trait Message extends Serializable

@SerialVersionUID(0)
case class Ping(s: String) extends Message

@SerialVersionUID(0)
case class Ack() extends Message
