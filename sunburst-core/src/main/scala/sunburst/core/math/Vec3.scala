package sunburst.core.math

/** A 3-dimensional vector
 *  @param x The x-coordinate of the vector
 *  @param y The y-coordinate of the vector
 *  @param z The z-coordinate of the vector
 */
case class Vec3 private (x: Float, y: Float, z: Float):
  /** Calculates a new vector by performing a component-wise addition.
   *  @param v The other vector to add to `this` vector
   *  @return A new vector with the sum of `this` and `v`
   */
  def +(v: Vec3): Vec3 = Vec3(x + v.x, y + v.y, z + v.z)

  /** Calculates a new vector by adding the scalar to all components.
   *  @param scalar The scalar to add to each component
   *  @return A new vector with the scalar added to each component
   */
  def +(scalar: Float): Vec3 = Vec3(x + scalar, y + scalar, z + scalar)

  /** Calculates a new vector by performing a component-wise subtraction.
   *  This operation is non-commutative.
   *  @param v The other vector to subtract from `this` vector
   *  @return A new vector with the difference between `this` and `v`
   */
  def -(v: Vec3): Vec3 = Vec3(x - v.x, y - v.y, z - v.z)

  /** Calculates a new vector by performing a component-wise subtraction.
   *  @param scalar The scalar to subtract from each component
   *  @return A new vector with the scalar subtracted from each component
   */
  def -(scalar: Float): Vec3 = Vec3(x - scalar, y - scalar, z - scalar)

  /** Calculates a new negated vector.
   *  @return A negated `this` vector
   */
  def unary_- : Vec3 = Vec3(-x, -y, -z)

  /** Calculates a new vector by performing a component-wise multiplication.
   *  @param scalar The scalar to multiply to each component
   *  @return A new vector scaled by the `scalar`
   */
  def *(scalar: Float): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)

  /** Calculates a new vector by performing a component-wise division.
   *  @param scalar The scalar to divide each component by
   *  @throws java.lang.IllegalArgumentException If the `scalar` is 0
   *  @return A new vector divide by the `scalar`
   */
  def /(scalar: Float): Vec3 =
    require(scalar != 0.0f, "/ by zero")
    this * (1.0f / scalar)

  /** Calculates the magnitude of the vector.
   *  @return The magnitude
   */
  def magnitude: Float = math.sqrt(magnitudeSquared).toFloat

  /** Calculates the squared magnitude of the vector.
   *  It's defined as `x*x + y*y + z*z`.
   *  @return The squared magnitude
   */
  def magnitudeSquared: Float = dot(this)

  /** Returns a vector on the unit sphere.
   *  @throws java.lang.IllegalArgumentException If the magnitude is 0
   *  @return The normalized vector
   */
  def normalized: Vec3 = this / magnitude

  /** Calculated the dot product of two vectors.
   *  @param v Right-hand side vector
   *  @return The dot product of `this` and `v`
   */
  infix def dot(v: Vec3): Float = x * v.x + y * v.y + z * v.z

  /** Calculates the cross product of two vectors
   *  @param v Right-hand side vector
   *  @return The cross product of `this` and `v`
   */
  infix def cross(v: Vec3): Vec3 = Vec3(
    y * v.z - z * v.y,
    z * v.x - x * v.z,
    x * v.y - y * v.x
  )

  def apply(c: Int): Float =
    require(c >= 0 && c <= 2, "component index out of bounds")
    if c == 0 then x
    else if c == 1 then y
    else z

object Vec3:
  /** Creates a new vector with each component equal to the scalar.
   *  @param s Scalar value for each component
   *  @return A new Vector(s, s, s)
   */
  def apply(s: Float): Vec3 = new Vec3(s, s, s)

  /** Creates a new vector with specified components.
   *  @param x The x-coordinate
   *  @param y The y-coordinate
   *  @param z The z-coordinate
   *  @return A new Vector(x, y ,z)
   */
  def apply(x: Float, y: Float, z: Float): Vec3 = new Vec3(x, y, z)

  /** Default zero-vector defined as Vector(0, 0, 0) */
  lazy val zero: Vec3 = Vec3(0)

  /** Default one-vector defined as Vector(1, 1, 1) */
  lazy val one: Vec3 = Vec3(1)

  extension (scalar: Float)
    def +(v: Vec3): Vec3 = v + scalar
    def -(v: Vec3): Vec3 = Vec3(scalar) - v
    def *(v: Vec3): Vec3 = v * scalar
