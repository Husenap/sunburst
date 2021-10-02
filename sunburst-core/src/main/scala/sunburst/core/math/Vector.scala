package sunburst.core.math

case class Vector private (val x: Float, val y: Float, val z: Float):
  def +(other: Vector) = Vector(x + other.x, y + other.y, z + other.z)
  def *(scalar: Float) = Vector(x * scalar, y * scalar, z * scalar)

case object Vector:
  def apply(s: Float)                     = new Vector(s, s, s)
  def apply(x: Float, y: Float, z: Float) = new Vector(x, y, z)
