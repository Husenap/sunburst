import imgui.ImGui
import imgui.ImGuiWindowClass
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiDockNodeFlags
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import sunburst.core.graphics.framework.*
import sunburst.core.window.Window

import java.nio.ByteBuffer

class Example extends sunburst.core.Application:
  var image: Image                 = compiletime.uninitialized
  var texture: Texture             = compiletime.uninitialized
  var shaderProgram: ShaderProgram = compiletime.uninitialized
  var vbo: Int                     = 0
  var vao: Int                     = 0
  var f: Float                     = 0f

  override def tickRate: Float = 1f / 60f

  override def init(): Unit =
    image = Image.fromFile("images/breakthrough.png")
    texture = Texture.fromImage(image)

    val vertexShader   =
      Shader.fromFile(ShaderType.Vertex, "shaders/mandelbrot.vert")
    val fragmentShader =
      Shader.fromFile(ShaderType.Fragment, "shaders/mandelbrot.frag")

    shaderProgram = ShaderProgram.fromShaders(vertexShader, fragmentShader)
    shaderProgram.error match
      case Some(e) => println(s"Failed to create shader program:\n$e")
      case None    => println("Successfully created shader program")

    val vertices =
      Array(-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.5f, 0.0f)

    vao = glGenVertexArrays()
    glBindVertexArray(vao)

    vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L)
    glEnableVertexAttribArray(0)

    glBindVertexArray(0)

    window.subscribe(this, e => println(s"$e"))

  override def tick(t: Float, dt: Float): Unit =
    f = math.sin(t).toFloat * 200f + 600f

  override def render(alpha: Float): Unit =
    glClear(GL_COLOR_BUFFER_BIT)
    shaderProgram.use()
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)

    ImGui.pushStyleColor(ImGuiCol.WindowBg, 0f, 0f, 0f, 0f)
    ImGui.dockSpaceOverViewport()
    ImGui.popStyleColor()

    ImGui.showDemoWindow()
    if ImGui.begin("Texture Viewer") then ImGui.image(texture.textureId, f, f)
    ImGui.end()

@main def main: Unit =
  try Example().run()
  catch case e: Exception => e.printStackTrace()
