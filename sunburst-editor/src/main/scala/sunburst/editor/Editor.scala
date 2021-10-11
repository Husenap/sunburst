package sunburst.editor

import imgui.ImGui

class Editor extends sunburst.core.Application:
  override def init(): Unit = ()

  override def tick(t: Float, dt: Float): Unit = ()

  override def render(alpha: Float): Unit =
    ImGui.dockSpaceOverViewport()
    ImGui.showDemoWindow()

@main def main: Unit =
  try Editor().run()
  catch case e: Exception => e.printStackTrace()
