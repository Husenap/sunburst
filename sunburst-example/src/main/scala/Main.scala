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
import sunburst.core.math.*
import sunburst.core.window.*

import java.awt.Window
import java.nio.ByteBuffer

class Example
    extends sunburst.core.Application(
      WindowOptions(
        width = 1000,
        height = 1000,
        title = "Sunburst Example",
        vSync = true
      )
    ):
  var texture: Texture             = compiletime.uninitialized
  var shaderProgram: ShaderProgram = compiletime.uninitialized
  var vbo: Int                     = 0
  var ebo: Int                     = 0
  var vao: Int                     = 0
  var f: Float                     = 0f
  var rotation                     = Quaternion()
  var transform                    = Mat4.Identity
  val FOV                          = 90f
  var projection                   = calcProjection(1, 1)
  val view                         = Mat4.translation(Vec3(0f, 0f, -3f))

  def calcProjection(w: Float, h: Float) =
    Mat4.perspectiveProjectionVertical(FOV, w / h, 0.1f, 100f)

  override def tickRate: Float = 1f / 24f

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
    shaderProgram.set[Int]("uTexture", 0)

    vao = glGenVertexArrays()
    glBindVertexArray(vao)

    vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, Cube.vertices, GL_STATIC_DRAW)
    val posLocation = shaderProgram.attributeLocation("aPos")
    val texLocation = shaderProgram.attributeLocation("aTexCoord")
    val stride      = 5 * 4
    glVertexAttribPointer(posLocation, 3, GL_FLOAT, false, stride, 0)
    glEnableVertexAttribArray(posLocation)
    glVertexAttribPointer(texLocation, 2, GL_FLOAT, false, stride, 3 * 4)
    glEnableVertexAttribArray(texLocation)

    ebo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, Cube.indices, GL_STATIC_DRAW)

    glBindVertexArray(0)

    window.subscribe(
      this,
      {
        case WindowEvent.KeyPress(GLFW_KEY_ESCAPE, _, _) =>
          window.close()
        case WindowEvent.Resize(w, h)                    =>
          glViewport(0, 0, w, h)
          projection = calcProjection(w, h)
        case _                                           =>
      }
    )

  override def tick(t: Float, dt: Float): Unit =
    f = math.sin(t).toFloat * 200f + 600f
    rotation *= Quaternion.fromAxisAndAngle(
      Vec3(
        math.cos(t * 0.9127f).toFloat,
        math.sin(t).toFloat,
        math.cos(t * 1.2137f).toFloat
      ),
      dt * 3f
    )
    transform = rotation.rotationMatrix

  lazy val cubePositions = Vector(
    Vec3(0.0f, 0.0f, 0.0f),
    Vec3(2.0f, 5.0f, 15.0f),
    Vec3(-1.5f, -2.2f, 2.5f),
    Vec3(-3.8f, -2.0f, 12.3f),
    Vec3(2.4f, -0.4f, 3.5f),
    Vec3(-1.7f, 3.0f, 7.5f),
    Vec3(1.3f, -2.0f, 2.5f),
    Vec3(1.5f, 2.0f, 2.5f),
    Vec3(1.5f, 0.2f, 1.5f),
    Vec3(-1.3f, 1.0f, 1.5f)
  )
  override def render(alpha: Float): Unit =
    glEnable(GL_DEPTH_TEST)
    glEnable(GL_CULL_FACE)
    glCullFace(GL_BACK)
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    shaderProgram.use()
    texture.bind(0)
    glBindVertexArray(vao)
    shaderProgram.set[Mat4]("uProjection", projection)
    shaderProgram.set[Mat4]("uView", view.inverse)
    for i <- cubePositions.indices do
      val angle = 20f * i
      val model =
        Quaternion
          .fromAxisAndAngle(
            Vec3(1, 0.3f, -0.5f),
            angle * math.Pi.toFloat / 180f
          )
          .rotationMatrix *
          Mat4.translation(cubePositions(i))
      shaderProgram.set[Mat4]("uModel", model)
      glDrawElements(GL_TRIANGLES, Cube.indices.length, GL_UNSIGNED_INT, 0)

    ImGui.pushStyleColor(ImGuiCol.WindowBg, 0f, 0f, 0f, 0f)
    ImGui.dockSpaceOverViewport()
    ImGui.popStyleColor()

@main def main: Unit =
  try Example().run()
  catch case e: Throwable => e.printStackTrace()
