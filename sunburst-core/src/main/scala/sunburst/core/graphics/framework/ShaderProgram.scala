package sunburst.core.graphics.framework

import org.lwjgl.opengl.GL20.*
import sunburst.core.math.*

class ShaderProgram private (val program: Int):
  def error: Option[String] =
    val log = glGetProgramInfoLog(program)
    if log.isBlank then None else Some(log)

  def use(): Unit =
    glUseProgram(program)

  def attributeLocation(name: String): Int =
    glGetAttribLocation(program, name)

  def uniformLocation(name: String): Int =
    glGetUniformLocation(program, name)

  def set[A](name: String, value: A): Unit =
    set(uniformLocation(name), value)

  def set[A](location: Int, value: A): Unit = value match
    case v: Int   => glUniform1i(location, v)
    case v: Float => glUniform1f(location, v)
    case v: Vec3  => glUniform3fv(location, v.toArray)
    case v: Vec4  => glUniform4fv(location, v.toArray)
    case v: Mat3  => glUniformMatrix3fv(location, false, v.toArray)
    case v: Mat4  => glUniformMatrix4fv(location, false, v.toArray)

object ShaderProgram:
  def fromShaders(shaders: Shader*): ShaderProgram =
    val program = glCreateProgram()
    for shader <- shaders do glAttachShader(program, shader.shader)
    glLinkProgram(program)

    new ShaderProgram(program)
