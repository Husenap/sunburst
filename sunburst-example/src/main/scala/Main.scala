import imgui.ImGui
import imgui.ImGuiWindowClass
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiDockNodeFlags
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import sunburst.core.graphics.framework.*
import sunburst.core.window.WindowEvent

import java.nio.ByteBuffer

class Example extends sunburst.core.Application:
  var texture: Texture             = compiletime.uninitialized
  var shaderProgram: ShaderProgram = compiletime.uninitialized
  var vbo: Int                     = 0
  var vao: Int                     = 0
  var f: Float                     = 0f
  val offset                       = Array[Float](0, 0)

  override def tickRate: Float = 1f / 60f

  override def init(): Unit =
    texture = Texture.fromImage(Image.fromFile("images/breakthrough.png"))

    val vertexShader   =
      Shader.fromFile(ShaderType.Vertex, "shaders/mandelbrot.vert")
    val fragmentShader =
      Shader.fromFile(ShaderType.Fragment, "shaders/mandelbrot.frag")

    shaderProgram = ShaderProgram.fromShaders(vertexShader, fragmentShader)
    shaderProgram.error match
      case Some(e) => println(s"Failed to create shader program:\n$e")
      case None    => println("Successfully created shader program")
    shaderProgram.use()
    shaderProgram.setInt("uTexture", 0)

    val vertices = Array(
      0.5f, 0.5f, 0.0f, 1f, 1f,   // top right
      0.5f, -0.5f, 0.0f, 1f, 0f,  // bottom right
      -0.5f, -0.5f, 0.0f, 0f, 0f, // bottom left
      -0.5f, 0.5f, 0.0f, 0f, 1f   // top left
    )
    val indices  = Array(0, 1, 3, 1, 2, 3)

    vao = glGenVertexArrays()
    glBindVertexArray(vao)

    vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)
    val posLocation = shaderProgram.attributeLocation("aPos")
    val texLocation = shaderProgram.attributeLocation("aTexCoord")
    val stride      = 5 * 4
    glVertexAttribPointer(posLocation, 3, GL_FLOAT, false, stride, 0)
    glEnableVertexAttribArray(posLocation)
    glVertexAttribPointer(texLocation, 2, GL_FLOAT, false, stride, 3 * 4)
    glEnableVertexAttribArray(texLocation)

    val ebo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

    glBindVertexArray(0)

    window.subscribe(
      this,
      {
        case WindowEvent.KeyPress(key, _, _) if key == GLFW_KEY_ESCAPE =>
          window.close()
        case _                                                         =>
      }
    )

  override def tick(t: Float, dt: Float): Unit =
    f = math.sin(t).toFloat * 200f + 600f

  override def render(alpha: Float): Unit =
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f)
    glClear(GL_COLOR_BUFFER_BIT)
    shaderProgram.use()
    glUniform2fv(shaderProgram.uniformLocation("uOffset"), offset)
    texture.bind(0)
    glBindVertexArray(vao)
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)

    ImGui.pushStyleColor(ImGuiCol.WindowBg, 0f, 0f, 0f, 0f)
    ImGui.dockSpaceOverViewport()
    ImGui.popStyleColor()

    ImGui.showDemoWindow()
    if ImGui.begin("Triangle") then ImGui.dragFloat2("Offset", offset, 0.1f)
    ImGui.end()

@main def main: Unit =
  try Example().run()
  catch case e: Throwable => e.printStackTrace()
