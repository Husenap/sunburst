import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer

class Image:
  private var pixels: ByteBuffer = null
  private val width = Array(0)
  private val height = Array(0)
  private val channels = Array(0)

  def this(imagePath: String) =
    this()
    val classloader = Thread.currentThread.getContextClassLoader
    val is = classloader.getResourceAsStream(imagePath)
    val bytes = is.readAllBytes
    val data = BufferUtils.createByteBuffer(bytes.length)
    data.put(bytes)
    data.flip()

    pixels = STBImage.stbi_load_from_memory(data, width, height, channels, 4)

  def getWidth() = width(0)
  def getHeight() = height(0)
  def getNumChannels() = channels(0)
  def getPixels() = pixels
