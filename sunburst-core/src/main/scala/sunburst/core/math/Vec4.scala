package sunburst.core.math

/** A 4-dimensional vector.
 *  @param x The x-coordinate of the vector
 *  @param y The y-coordinate of the vector
 *  @param z The z-coordinate of the vector
 *  @param w The w-coordinate of the vector
 */
case class Vec4 private (x: Float, y: Float, z: Float, w: Float):
  def +(v: Vec4): Vec4       = Vec4(x + v.x, y + v.y, z + v.z, w + v.w)
  def +(scalar: Float): Vec4 =
    Vec4(x + scalar, y + scalar, z + scalar, w + scalar)

  def -(v: Vec4): Vec4       = Vec4(x - v.x, y - v.y, z - v.z, w - v.w)
  def -(scalar: Float): Vec4 =
    Vec4(x - scalar, y - scalar, z - scalar, w - scalar)
  lazy val unary_- : Vec4    = Vec4(-x, -y, -z, -w)

  def *(scalar: Float): Vec4 =
    Vec4(x * scalar, y * scalar, z * scalar, w * scalar)

  def /(scalar: Float): Vec4 =
    require(scalar != 0f, "/ by zero")
    Vec4(x / scalar, y / scalar, z / scalar, w / scalar)

  lazy val normalized              = this / magnitude
  lazy val magnitude: Float        = math.sqrt(magnitudeSquared).toFloat
  lazy val magnitudeSquared: Float = dot(this)
  infix def dot(v: Vec4): Float    = x * v.x + y * v.y + z * v.z + w * v.w

  lazy val toArray: Array[Float] = Array(x, y, z, w)

object Vec4:
  def apply(s: Float): Vec4 = new Vec4(s, s, s, s)

  def apply(x: Float, y: Float, z: Float, w: Float): Vec4 = new Vec4(x, y, z, w)

  final val Zero    = Vec4(0)
  final val One     = Vec4(1)
  final val Right   = Vec4(1, 0, 0, 0)
  final val Up      = Vec4(0, 1, 0, 0)
  final val Forward = Vec4(0, 0, 1, 0)

  extension (scalar: Float)
    def +(v: Vec4): Vec4 = v + scalar
    def -(v: Vec4): Vec4 = Vec4(scalar) - v
    def *(v: Vec4): Vec4 = v * scalar
