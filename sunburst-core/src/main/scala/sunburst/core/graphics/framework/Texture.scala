package sunburst.core.graphics.framework

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL30.*

class Texture private:
  private val _textureId = Array(0)

  def bind(slot: Int): Unit =
    glActiveTexture(GL_TEXTURE0 + slot)
    glBindTexture(GL_TEXTURE_2D, textureId)

  def textureId = _textureId(0)

object Texture:
  def fromImage(image: Image): Texture =
    val texture = new Texture()
    glGenTextures(texture._textureId)
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, texture._textureId(0))

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
    glTexParameteri(
      GL_TEXTURE_2D,
      GL_TEXTURE_MIN_FILTER,
      GL_LINEAR_MIPMAP_LINEAR
    )
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    glTexImage2D(
      GL_TEXTURE_2D,
      0,
      GL_RGBA,
      image.width,
      image.height,
      0,
      GL_RGBA,
      GL_UNSIGNED_BYTE,
      image.pixels
    )
    glGenerateMipmap(GL_TEXTURE_2D)

    texture
