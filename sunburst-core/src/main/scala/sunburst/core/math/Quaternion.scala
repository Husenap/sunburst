package sunburst.core.math

case class Quaternion private (w: Float, v: Vec3):
  def +(q: Quaternion): Quaternion = Quaternion(w + q.w, v + q.v)

  def -(q: Quaternion): Quaternion = Quaternion(w - q.w, v - q.v)

  def *(q: Quaternion): Quaternion =
    Quaternion(w * q.w - (v dot q.v), w * q.v + q.w * v + (v cross q.v))
  def *(scalar: Float): Quaternion = Quaternion(w * scalar, v * scalar)

  def /(q: Quaternion): Quaternion = this * q.inverse
  def /(scalar: Float): Quaternion = Quaternion(w / scalar, v / scalar)

  def magnitudeSquared: Float = w * w + v.magnitudeSquared
  def magnitude: Float        = math.sqrt(magnitudeSquared).toFloat

  def normalized: Quaternion = this / magnitude

  def conjugate: Quaternion = Quaternion(w, -v)
  def inverse: Quaternion =
    assert(magnitudeSquared != 0.0f, "zero-quaternion doesn't have an inverse")
    conjugate / magnitudeSquared

  def rotationMatrix: Mat3 =
    Mat3(
      Vec3(1 - 2 * (y * y + z * z), 2 * (x * y - w * z), 2 * (z * x + w * y)),
      Vec3(2 * (x * y + w * z), 1 - 2 * (x * x + z * z), 2 * (y * z - w * x)),
      Vec3(2 * (z * x - w * y), 2 * (y * z + w * x), 1 - 2 * (x * x + y * y))
    )

  def x = v.x
  def y = v.y
  def z = v.z

object Quaternion:
  def apply(): Quaternion                                       = new Quaternion(1, Vec3(0, 0, 0))
  def apply(w: Float, x: Float, y: Float, z: Float): Quaternion =
    new Quaternion(w, Vec3(x, y, z))
  def apply(w: Float, v: Vec3): Quaternion                      = new Quaternion(w, v)

  def fromAxisAndAngle(axis: Vec3, angle: Float): Quaternion =
    Quaternion(
      math.cos(angle / 2).toFloat,
      axis.normalized * math.sin(angle / 2).toFloat
    )
  def rotateAroundAxis(
      v: Vec3,
      axis: Vec3,
      angle: Float
  ): Vec3 =
    val q  = fromAxisAndAngle(axis.normalized, angle)
    val qv = Quaternion(0, v)
    (q * qv * q.inverse).v

  def lerp(a: Quaternion, b: Quaternion, alpha: Float): Quaternion  =
    a + (b - a) * alpha
  def nlerp(a: Quaternion, b: Quaternion, alpha: Float): Quaternion =
    lerp(a, b, alpha).normalized
