package sunburst.core.math

case class Mat4 private (x: Vec4, y: Vec4, z: Vec4, w: Vec4):
  def +(m: Mat4) = Mat4(x + m.x, y + m.y, z + m.z, w + m.w)
  def -(m: Mat4) = Mat4(x - m.x, y - m.y, z - m.z, w - m.w)

  def *(m: Mat4): Mat4  = Mat4(
    Vec4(
      x.x * m.x.x + x.y * m.y.x + x.z * m.z.x + x.w * m.w.x,
      x.x * m.x.y + x.y * m.y.y + x.z * m.z.y + x.w * m.w.y,
      x.x * m.x.z + x.y * m.y.z + x.z * m.z.z + x.w * m.w.z,
      x.x * m.x.w + x.y * m.y.w + x.z * m.z.w + x.w * m.w.w
    ),
    Vec4(
      y.x * m.x.x + y.y * m.y.x + y.z * m.z.x + y.w * m.w.x,
      y.x * m.x.y + y.y * m.y.y + y.z * m.z.y + y.w * m.w.y,
      y.x * m.x.z + y.y * m.y.z + y.z * m.z.z + y.w * m.w.z,
      y.x * m.x.w + y.y * m.y.w + y.z * m.z.w + y.w * m.w.w
    ),
    Vec4(
      z.x * m.x.x + z.y * m.y.x + z.z * m.z.x + z.w * m.w.x,
      z.x * m.x.y + z.y * m.y.y + z.z * m.z.y + z.w * m.w.y,
      z.x * m.x.z + z.y * m.y.z + z.z * m.z.z + z.w * m.w.z,
      z.x * m.x.w + z.y * m.y.w + z.z * m.z.w + z.w * m.w.w
    ),
    Vec4(
      w.x * m.x.x + w.y * m.y.x + w.z * m.z.x + w.w * m.w.x,
      w.x * m.x.y + w.y * m.y.y + w.z * m.z.y + w.w * m.w.y,
      w.x * m.x.z + w.y * m.y.z + w.z * m.z.z + w.w * m.w.z,
      w.x * m.x.w + w.y * m.y.w + w.z * m.z.w + w.w * m.w.w
    )
  )
  def *(s: Float): Mat4 = Mat4(x * s, y * s, z * s, w * s)
  def *(v: Vec4): Vec4  =
    Vec4(
      x.x * v.x + x.y * v.y + x.z * v.z + x.w * v.w,
      y.x * v.x + y.y * v.y + y.z * v.z + y.w * v.w,
      z.x * v.x + z.y * v.y + z.z * v.z + z.w * v.w,
      w.x * v.x + w.y * v.y + w.z * v.z + w.w * v.w
    )

  lazy val transposed: Mat4 = Mat4(
    // format: off
    x.x, y.x, z.x, w.x,
    x.y, y.y, z.y, w.y,
    x.z, y.z, z.z, w.z,
    x.w, y.w, z.w, w.w
    // format: on
  )

  lazy val determinant: Float =
    x.x * ((y.y * z.z * w.w + y.z * z.w * w.y + z.y * w.z * y.w) -
      (y.w * z.z * w.y + z.w * w.z * y.y + y.z * z.y * w.w)) -
      x.y * ((y.x * z.z * w.w + y.z * z.w * w.x + z.x * w.z * y.w) -
        (y.w * z.z * w.x + z.w * w.z * y.x + y.z * z.x * w.w)) +
      x.z * ((y.x * z.y * w.w + y.y * z.w * w.x + z.x * w.y * y.w) -
        (y.w * z.y * w.x + z.w * w.y * y.x + y.y * z.x * w.w)) -
      x.w * ((y.x * z.y * w.z + y.y * z.z * w.x + z.x * w.y * y.z) -
        (y.z * z.y * w.x + z.z * w.y * y.x + y.y * z.x * w.z))

  lazy val inverse: Mat4 =
    assert(determinant != 0f, "Matrix Inverse doesn't exist!")
    Mat4(
      Vec4(
        y.y * z.z * w.w - y.y * z.w * w.z - z.y * y.z * w.w + z.y * y.w * w.z + w.y * y.z * z.w - w.y * y.w * z.z,
        -x.y * z.z * w.w + x.y * z.w * w.z + z.y * x.z * w.w - z.y * x.w * w.z - w.y * x.z * z.w + w.y * x.w * z.z,
        x.y * y.z * w.w - x.y * y.w * w.z - y.y * x.z * w.w + y.y * x.w * w.z + w.y * x.z * y.w - w.y * x.w * y.z,
        -x.y * y.z * z.w + x.y * y.w * z.z + y.y * x.z * z.w - y.y * x.w * z.z - z.y * x.z * y.w + z.y * x.w * y.z
      ),
      Vec4(
        -y.x * z.z * w.w + y.x * z.w * w.z + z.x * y.z * w.w - z.x * y.w * w.z - w.x * y.z * z.w + w.x * y.w * z.z,
        x.x * z.z * w.w - x.x * z.w * w.z - z.x * x.z * w.w + z.x * x.w * w.z + w.x * x.z * z.w - w.x * x.w * z.z,
        -x.x * y.z * w.w + x.x * y.w * w.z + y.x * x.z * w.w - y.x * x.w * w.z - w.x * x.z * y.w + w.x * x.w * y.z,
        x.x * y.z * z.w - x.x * y.w * z.z - y.x * x.z * z.w + y.x * x.w * z.z + z.x * x.z * y.w - z.x * x.w * y.z
      ),
      Vec4(
        y.x * z.y * w.w - y.x * z.w * w.y - z.x * y.y * w.w + z.x * y.w * w.y + w.x * y.y * z.w - w.x * y.w * z.y,
        -x.x * z.y * w.w + x.x * z.w * w.y + z.x * x.y * w.w - z.x * x.w * w.y - w.x * x.y * z.w + w.x * x.w * z.y,
        x.x * y.y * w.w - x.x * y.w * w.y - y.x * x.y * w.w + y.x * x.w * w.y + w.x * x.y * y.w - w.x * x.w * y.y,
        -x.x * y.y * z.w + x.x * y.w * z.y + y.x * x.y * z.w - y.x * x.w * z.y - z.x * x.y * y.w + z.x * x.w * y.y
      ),
      Vec4(
        -y.x * z.y * w.z + y.x * z.z * w.y + z.x * y.y * w.z - z.x * y.z * w.y - w.x * y.y * z.z + w.x * y.z * z.y,
        x.x * z.y * w.z - x.x * z.z * w.y - z.x * x.y * w.z + z.x * x.z * w.y + w.x * x.y * z.z - w.x * x.z * z.y,
        -x.x * y.y * w.z + x.x * y.z * w.y + y.x * x.y * w.z - y.x * x.z * w.y - w.x * x.y * y.z + w.x * x.z * y.y,
        x.x * y.y * z.z - x.x * y.z * z.y - y.x * x.y * z.z + y.x * x.z * z.y + z.x * x.y * y.z - z.x * x.z * y.y
      )
    ) * (1f / determinant)

  lazy val toArray: Array[Float] = Array(
    // format: off
    x.x, x.y, x.z, x.w,
    y.x, y.y, y.z, y.w,
    z.x, z.y, z.z, z.w,
    w.x, w.y, w.z, w.w
    // format: on
  )

object Mat4:
  def apply(x: Vec4, y: Vec4, z: Vec4, w: Vec4) = new Mat4(x, y, z, w)

  def apply(
    // format: off
      m00: Float = 1, m01: Float = 0, m02: Float = 0, m03: Float = 0,
      m10: Float = 0, m11: Float = 1, m12: Float = 0, m13: Float = 0,
      m20: Float = 0, m21: Float = 0, m22: Float = 1, m23: Float = 0,
      m30: Float = 0, m31: Float = 0, m32: Float = 0, m33: Float = 1
    // format: on
  ): Mat4 = Mat4(
    Vec4(m00, m01, m02, m03),
    Vec4(m10, m11, m12, m13),
    Vec4(m20, m21, m22, m23),
    Vec4(m30, m31, m32, m33)
  )

  def apply(m: Mat3): Mat4 =
    Mat4(
      // format: off
      m.x.x, m.x.y, m.x.z, 0f,
      m.y.x, m.y.y, m.y.z, 0f,
      m.z.x, m.z.y, m.z.z, 0f
      // format: on
    )

  def perspectiveMatrix(
      fovY: Float,
      aspect: Float,
      near: Float,
      far: Float
  ): Mat4 =
    val fov = fovY / aspect
    val b   = 1f / math.tan(fov * math.Pi / 360f).toFloat
    val a   = b / aspect
    val c   = far / (far - near)
    val d   = 1f
    val e   = -near * far / (far - near)
    Mat4(a, 0, 0, 0, 0, b, 0, 0, 0, 0, c, d, 0, 0, e, 0)

  final val Identity = Mat4()
