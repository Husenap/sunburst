import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL30.*

class Texture:
  private val texture = Array(0)

  def this(image: Image) =
    this()
    fromImage(image)

  def fromImage(image: Image) =
    glGenTextures(texture)
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, texture(0))

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    glTexImage2D(
      GL_TEXTURE_2D,
      0,
      GL_RGBA,
      image.getWidth(),
      image.getHeight(),
      0,
      GL_RGBA,
      GL_UNSIGNED_BYTE,
      image.getPixels()
    )
    glGenerateMipmap(GL_TEXTURE_2D)

  def getId() = texture(0)
