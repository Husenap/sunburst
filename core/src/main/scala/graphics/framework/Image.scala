package sunburst.graphics.framework

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer

class Image:
  private var _pixels: ByteBuffer = null
  private val _width              = Array(0)
  private val _height             = Array(0)
  private val _channels           = Array(0)

  def this(imagePath: String) =
    this()
    val classloader = Thread.currentThread.getContextClassLoader
    val is          = classloader.getResourceAsStream(imagePath)
    val bytes       = is.readAllBytes()
    val data        = BufferUtils.createByteBuffer(bytes.length)
    data.put(bytes)
    data.flip()

    _pixels =
      STBImage.stbi_load_from_memory(data, _width, _height, _channels, 4)

  def width    = _width(0)
  def height   = _height(0)
  def channels = _channels(0)
  def pixels   = _pixels
