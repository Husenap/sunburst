package sunburst.core.graphics.framework

import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL32.*
import org.lwjgl.opengl.GL43.*
import sunburst.core.io.FileLocator

enum ShaderType:
  case Vertex, Fragment, Compute, Geometry

class Shader private (val shader: Int):
  def error: Option[String] =
    val log = glGetShaderInfoLog(shader)
    if log.isBlank then None else Some(log)

object Shader:
  def fromFile(shaderType: ShaderType, shaderPath: String): Shader =
    val shader = glCreateShader(shaderTypeToInt(shaderType))
    val source = FileLocator.readFileToString(shaderPath)

    glShaderSource(shader, source)
    glCompileShader(shader)

    new Shader(shader)

  private def shaderTypeToInt(shaderType: ShaderType) = shaderType match
    case ShaderType.Vertex   => GL_VERTEX_SHADER
    case ShaderType.Fragment => GL_FRAGMENT_SHADER
    case ShaderType.Geometry => GL_GEOMETRY_SHADER
    case ShaderType.Compute  => GL_COMPUTE_SHADER
