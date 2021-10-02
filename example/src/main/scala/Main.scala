import sunburst.core.window.Window
import imgui.ImGui
import sunburst.core.graphics.framework.*

class Example extends sunburst.core.Application:
  var image: Image     = null
  var texture: Texture = null

  override def init(): Unit =
    image = Image.fromFile("breakthrough.png")
    texture = Texture.fromImage(image)

  override def tick(): Unit =
    ImGui.dockSpaceOverViewport()
    ImGui.showDemoWindow()

    if ImGui.begin("Texture Viewer") then
      ImGui.image(texture.textureId, 80, 80)
      ImGui.image(texture.textureId, 800, 800)
    ImGui.end()

@main def main: Unit =
  try Example().run()
  catch case e: Exception => e.printStackTrace()
