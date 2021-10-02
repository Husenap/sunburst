package sunburst.core.math

case class Quaternion private (
    val w: Float,
    val x: Float,
    val y: Float,
    val z: Float
):
  ???

object Quaternion:
  def apply(): Quaternion = new Quaternion(0, 0, 0, 0)
