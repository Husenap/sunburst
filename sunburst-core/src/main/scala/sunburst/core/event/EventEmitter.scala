package sunburst.core.event

import scala.collection.mutable.ArrayBuffer

trait EventEmitter[A]:
  private val subscribers = ArrayBuffer[A => Unit]()

  def onEvent(callback: A => Unit): Unit =
    subscribers += callback

  def broadcast(event: A): Unit = subscribers.foreach(_(event))
