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
  def unary_- : Vec4         = Vec4(-x, -y, -z, -w)

  def *(scalar: Float): Vec4 =
    Vec4(x * scalar, y * scalar, z * scalar, w * scalar)

  def /(scalar: Float): Vec4 =
    require(scalar != 0f, "/ by zero")
    Vec4(x / scalar, y / scalar, z / scalar, w / scalar)

  def normalized                = this / magnitude
  def magnitude: Float          = math.sqrt(magnitudeSquared).toFloat
  def magnitudeSquared: Float   = dot(this)
  infix def dot(v: Vec4): Float = x * v.x + y * v.y + z * v.z + w * v.w

object Vec4:
  /** Creates a new vector with each component equal to the scalar.
   *  @param s Scalar value for each component
   *  @return A new Vec4(s, s, s, s)
   */
  def apply(s: Float): Vec4 = new Vec4(s, s, s, s)

  /** Creates a new vector with specified components.
   *  @param x The x-coordinate
   *  @param y The y-coordinate
   *  @param z The z-coordinate
   *  @param w The w-coordinate
   *  @return A new Vec4(x, y, z, w)
   */
  def apply(x: Float, y: Float, z: Float, w: Float): Vec4 = new Vec4(x, y, z, w)

  /** Default zero-vector */
  lazy val zero: Vec4 = Vec4(0)

  /** Default one-vector */
  lazy val one: Vec4 = Vec4(1)

  extension (scalar: Float)
    def +(v: Vec4): Vec4 = v + scalar
    def -(v: Vec4): Vec4 = Vec4(scalar) - v
    def *(v: Vec4): Vec4 = v * scalar
