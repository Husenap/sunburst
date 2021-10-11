package sunburst.core.math

case class Mat3 private (x: Vec3, y: Vec3, z: Vec3):
  def +(m: Mat3) = Mat3(x + m.x, y + m.y, z + m.z)
  def -(m: Mat3) = Mat3(x - m.x, y - m.y, z - m.z)

  def *(m: Mat3): Mat3  = Mat3(
    Vec3(
      x.x * m.x.x + x.y * m.y.x + x.z * m.z.x,
      x.x * m.x.y + x.y * m.y.y + x.z * m.z.y,
      x.x * m.x.z + x.y * m.y.z + x.z * m.z.z
    ),
    Vec3(
      y.x * m.x.x + y.y * m.y.x + y.z * m.z.x,
      y.x * m.x.y + y.y * m.y.y + y.z * m.z.y,
      y.x * m.x.z + y.y * m.y.z + y.z * m.z.z
    ),
    Vec3(
      z.x * m.x.x + z.y * m.y.x + z.z * m.z.x,
      z.x * m.x.y + z.y * m.y.y + z.z * m.z.y,
      z.x * m.x.z + z.y * m.y.z + z.z * m.z.z
    )
  )
  def *(s: Float): Mat3 = Mat3(x * s, y * s, z * s)
  def *(v: Vec3): Vec3  =
    Vec3(
      x.x * v.x + x.y * v.y + x.z * v.z,
      y.x * v.x + y.y * v.y + y.z * v.z,
      z.x * v.x + z.y * v.y + z.z * v.z
    )

  def transposed: Mat3 = Mat3(x.x, y.x, z.x, x.y, y.y, z.y, x.z, y.z, z.z)

  def determinant: Float =
    (x.x * y.y * z.z + x.y * y.z * z.x + x.z * y.x * z.y) - (z.x * y.y * x.z + z.y * y.z * x.x + z.z * y.x * x.y)

  def inverse: Mat3 =
    val det = determinant
    assert(det != 0f, "Matrix Inverse doesn't exist!")
    Mat3(
      +(y.y * z.z - z.y * y.z),
      -(x.y * z.z - z.y * x.z),
      +(x.y * y.z - y.y * x.z),
      -(y.x * z.z - z.x * y.z),
      +(x.x * z.z - z.x * x.z),
      -(x.x * y.z - y.x * x.z),
      +(y.x * z.y - z.x * y.y),
      -(x.x * z.y - z.x * x.y),
      +(x.x * y.y - y.x * x.y)
    ) * (1f / det)

  def apply(row: Int): Vec3 =
    require(row >= 0 && row <= 2, "component index out of bounds")
    if row == 0 then x
    else if row == 1 then y
    else z

object Mat3:
  def apply(x: Vec3, y: Vec3, z: Vec3) = new Mat3(x, y, z)

  def apply(
      m00: Float = 1,
      m01: Float = 0,
      m02: Float = 0,
      m10: Float = 0,
      m11: Float = 1,
      m12: Float = 0,
      m20: Float = 0,
      m21: Float = 0,
      m22: Float = 1
  ): Mat3 = Mat3(Vec3(m00, m01, m02), Vec3(m10, m11, m12), Vec3(m20, m21, m22))

  final val Identity = Mat3()
