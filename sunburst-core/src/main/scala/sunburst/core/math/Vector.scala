package sunburst.core.math

case class Vector private (val x: Float, val y: Float, val z: Float):
  def +(v: Vector): Vector     = Vector(x + v.x, y + v.y, z + v.z)
  def -(v: Vector): Vector     = Vector(x - v.x, y - v.y, z - v.z)
  def *(scalar: Float): Vector = Vector(x * scalar, y * scalar, z * scalar)
  def /(scalar: Float): Vector =
    if scalar == 0.0f then throw new java.lang.ArithmeticException("/ by zero")
    this * (1.0f / scalar)

  def dot(v: Vector): Float = x * v.x + y * v.y + z * v.z
  def length: Float         = math.sqrt(dot(this)).toFloat

case object Vector:
  def apply(s: Float)                     = new Vector(s, s, s)
  def apply(x: Float, y: Float, z: Float) = new Vector(x, y, z)
