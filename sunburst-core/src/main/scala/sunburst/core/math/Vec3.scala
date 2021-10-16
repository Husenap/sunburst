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
  def unary_- : Vec3         = Vec3(-x, -y, -z)
  def *(scalar: Float): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)

  def /(scalar: Float): Vec3 =
    require(scalar != 0f, "/ by zero")
    Vec3(x / scalar, y / scalar, z / scalar)

  def magnitude: Float        = math.sqrt(magnitudeSquared).toFloat
  def magnitudeSquared: Float = dot(this)

  def normalized: Vec3          = this / magnitude
  infix def dot(v: Vec3): Float = (x * v.x + y * v.y + z * v.z).toFloat

  /** Calculates the cross product of two vectors
   *  @param v Right-hand side vector
   *  @return The cross product of `this` and `v`
   */
  infix def cross(v: Vec3) = Vec3(
    y * v.z - z * v.y,
    z * v.x - x * v.z,
    x * v.y - y * v.x
  )

  lazy val toArray: Array[Float] = Array(x, y, z)

object Vec3:
  /** Creates a new vector with each component equal to the scalar.
   *  @param s Scalar value for each component
   *  @return A new Vec3(s, s, s)
   */
  def apply(s: Float) = new Vec3(s, s, s)

  /** Creates a new vector with specified components.
   *  @param x The x-coordinate
   *  @param y The y-coordinate
   *  @param z The z-coordinate
   *  @return A new Vec3(x, y ,z)
   */
  def apply(x: Float, y: Float, z: Float) = new Vec3(x, y, z)

  final val Zero = Vec3(0f)
  final val One  = Vec3(1f)

  extension (scalar: Float)
    def +(v: Vec3) = v + scalar
    def -(v: Vec3) = Vec3(scalar) - v
    def *(v: Vec3) = v * scalar
