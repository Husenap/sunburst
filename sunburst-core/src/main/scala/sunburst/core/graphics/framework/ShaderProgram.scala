package sunburst.core.graphics.framework

import org.lwjgl.opengl.GL20.*

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

  def setFloat(name: String, value: Float): Unit  =
    setFloat(uniformLocation(name), value)
  def setFloat(location: Int, value: Float): Unit =
    glUniform1f(location, value)
  def setInt(name: String, value: Int): Unit      =
    setInt(uniformLocation(name), value)
  def setInt(location: Int, value: Int): Unit     =
    glUniform1i(location, value)

object ShaderProgram:
  def fromShaders(shaders: Shader*): ShaderProgram =
    val program = glCreateProgram()
    for shader <- shaders do glAttachShader(program, shader.shader)
    glLinkProgram(program)

    new ShaderProgram(program)
