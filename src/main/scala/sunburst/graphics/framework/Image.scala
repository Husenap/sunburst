package sunburst.graphics.framework

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer

class Image private:
  private var _pixels: ByteBuffer = null
  private val _width              = Array(0)
  private val _height             = Array(0)
  private val _channels           = Array(0)

  def width    = _width(0)
  def height   = _height(0)
  def channels = _channels(0)
  def pixels   = _pixels

object Image:
  def fromFile(imagePath: String): Image =
    val image       = new Image()
    val classloader = Thread.currentThread.getContextClassLoader
    val is          = classloader.getResourceAsStream(imagePath)
    val bytes       = is.readAllBytes()
    val data        = BufferUtils.createByteBuffer(bytes.length)
    data.put(bytes)
    data.flip()

    image._pixels = STBImage.stbi_load_from_memory(
      data,
      image._width,
      image._height,
      image._channels,
      4
    )

    image
