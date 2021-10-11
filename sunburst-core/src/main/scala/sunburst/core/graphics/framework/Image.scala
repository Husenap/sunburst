package sunburst.core.graphics.framework

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer
import sunburst.core.io.FileLocator

class Image private (
    val pixels: ByteBuffer,
    val width: Int,
    val height: Int,
    val channels: Int
)

object Image:
  def fromFile(imagePath: String): Image =
    val data = FileLocator.readFileToByteBuffer(imagePath)

    val width    = Array(0)
    val height   = Array(0)
    val channels = Array(0)
    val pixels   = STBImage.stbi_load_from_memory(
      data,
      width,
      height,
      channels,
      4
    )

    new Image(pixels, width(0), height(0), channels(0))
