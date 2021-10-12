package sunburst.core.graphics.framework

import org.lwjgl.opengl.GL20.*

class ShaderProgram private (val program: Int):
  def error: Option[String] =
    val log = glGetProgramInfoLog(program)
    if log.isBlank then None else Some(log)

  def attributeLocation(name: String): Int =
    glGetAttribLocation(program, name)

  def uniformLocation(name: String): Int =
    glGetUniformLocation(program, name)

  def use(): Unit =
    glUseProgram(program)

object ShaderProgram:
  def fromShaders(shaders: Shader*): ShaderProgram =
    val program = glCreateProgram()
    for shader <- shaders do glAttachShader(program, shader.shader)
    glLinkProgram(program)

    new ShaderProgram(program)
