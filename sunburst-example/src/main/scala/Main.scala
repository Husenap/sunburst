import sunburst.core.window.Window
import imgui.ImGui
import sunburst.core.graphics.framework.*

class Example extends sunburst.core.Application:
  var image: Image                 = _
  var texture: Texture             = _
  var shaderProgram: ShaderProgram = _

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

  override def tick(t: Float, dt: Float): Unit = ()

  override def render(alpha: Float): Unit =
    ImGui.dockSpaceOverViewport()
    ImGui.showDemoWindow()

    if ImGui.begin("Texture Viewer") then
      ImGui.image(texture.textureId, 800, 800)
    ImGui.end()

@main def main: Unit =
  try Example().run()
  catch case e: Exception => e.printStackTrace()
