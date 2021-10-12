package sunburst.core.event

import scala.collection.mutable

trait EventEmitter[A]:
  private val subscribers = mutable.Map[Object, A => Unit]()

  def subscribe(subscriber: Object, callback: A => Unit): Unit =
    subscribers += subscriber -> callback

  def unsubscribe(subscriber: Object): Unit = subscribers -= subscriber

  def broadcast(event: A): Unit = subscribers.foreach((_, cb) => cb(event))
