package sunburst.core.math

import math.Numeric.Implicits.*

/** A 3-dimensional vector.
 *  @param x The x-coordinate of the vector
 *  @param y The y-coordinate of the vector
 *  @param z The z-coordinate of the vector
 */
case class Vec3 private (
    x: Float,
    y: Float,
    z: Float
):
  def +(v: Vec3): Vec3       = Vec3(x + v.x, y + v.y, z + v.z)
  def +(scalar: Float): Vec3 = Vec3(x + scalar, y + scalar, z + scalar)
  def -(v: Vec3): Vec3       = Vec3(x - v.x, y - v.y, z - v.z)
  def -(scalar: Float): Vec3 = Vec3(x - scalar, y - scalar, z - scalar)
  lazy val unary_- : Vec3    = Vec3(-x, -y, -z)
  def *(scalar: Float): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)

  def /(scalar: Float): Vec3 =
    require(scalar != 0f, "/ by zero")
    Vec3(x / scalar, y / scalar, z / scalar)

  lazy val magnitude: Float        = math.sqrt(magnitudeSquared).toFloat
  lazy val magnitudeSquared: Float = dot(this)

  lazy val normalized: Vec3     = this / magnitude
  infix def dot(v: Vec3): Float = (x * v.x + y * v.y + z * v.z).toFloat

  infix def cross(v: Vec3) = Vec3(
    y * v.z - z * v.y,
    z * v.x - x * v.z,
    x * v.y - y * v.x
  )

  lazy val toArray: Array[Float] = Array(x, y, z)

object Vec3:
  def apply(s: Float) = new Vec3(s, s, s)

  def apply(x: Float, y: Float, z: Float) = new Vec3(x, y, z)

  final val Zero    = Vec3(0)
  final val One     = Vec3(1)
  final val Right   = Vec3(1, 0, 0)
  final val Up      = Vec3(0, 1, 0)
  final val Forward = Vec3(0, 0, 1)

  extension (scalar: Float)
    def +(v: Vec3) = v + scalar
    def -(v: Vec3) = Vec3(scalar) - v
    def *(v: Vec3) = v * scalar
