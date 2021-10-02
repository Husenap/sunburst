package sunburst.core.graphics.framework

import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiConfigFlags
import imgui.flag.ImGuiDir
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import org.lwjgl.glfw.GLFW.*

object ImGuiWrapper:
  private lazy val imGuiGlfw = ImGuiImplGlfw()
  private lazy val imGuiGl3  = ImGuiImplGl3()

  def init(handle: Long, glslVersion: String): Unit =
    initImGui()
    imGuiGlfw.init(handle, true)
    imGuiGl3.init(glslVersion)

  def dispose(): Unit =
    imGuiGl3.dispose()
    imGuiGlfw.dispose()
    ImGui.destroyContext()

  def newFrame(): Unit =
    imGuiGlfw.newFrame()
    ImGui.newFrame()

  def render(): Unit =
    ImGui.render()
    imGuiGl3.renderDrawData(ImGui.getDrawData())

    if ImGui.getIO.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable) then
      val backupWindowHandle = glfwGetCurrentContext()
      ImGui.updatePlatformWindows()
      ImGui.renderPlatformWindowsDefault()
      glfwMakeContextCurrent(backupWindowHandle)

  private def initImGui(): Unit =
    ImGui.createContext()
    ImGui.getIO.setConfigFlags(ImGuiConfigFlags.ViewportsEnable)
    ImGui.getIO.setConfigFlags(ImGuiConfigFlags.DockingEnable)
    setupStyle()

    ImGui.getIO.setIniFilename(
      sunburst.core.io.FileLocator.locateTempFile("imgui.ini").toString
    )

  private def setupStyle(): Unit =
    val style  = ImGui.getStyle()
    val colors = style.getColors()

    style.setColor(ImGuiCol.Text, 1.00f, 1.00f, 1.00f, 1.00f)
    style.setColor(ImGuiCol.TextDisabled, 0.40f, 0.40f, 0.40f, 1.00f)
    style.setColor(ImGuiCol.WindowBg, 0.25f, 0.25f, 0.25f, 1.00f)
    style.setColor(ImGuiCol.ChildBg, 0.25f, 0.25f, 0.25f, 1.00f)
    style.setColor(ImGuiCol.PopupBg, 0.25f, 0.25f, 0.25f, 1.00f)
    style.setColor(ImGuiCol.Border, 0.12f, 0.12f, 0.12f, 0.71f)
    style.setColor(ImGuiCol.BorderShadow, 1.00f, 1.00f, 1.00f, 0.06f)
    style.setColor(ImGuiCol.FrameBg, 0.42f, 0.42f, 0.42f, 0.54f)
    style.setColor(ImGuiCol.FrameBgHovered, 0.42f, 0.42f, 0.42f, 0.40f)
    style.setColor(ImGuiCol.FrameBgActive, 0.56f, 0.56f, 0.56f, 0.67f)
    style.setColor(ImGuiCol.TitleBg, 0.19f, 0.19f, 0.19f, 1.00f)
    style.setColor(ImGuiCol.TitleBgActive, 0.22f, 0.22f, 0.22f, 1.00f)
    style.setColor(ImGuiCol.TitleBgCollapsed, 0.17f, 0.17f, 0.17f, 0.90f)
    style.setColor(ImGuiCol.MenuBarBg, 0.33f, 0.33f, 0.33f, 0.75f)
    style.setColor(ImGuiCol.ScrollbarBg, 0.24f, 0.24f, 0.24f, 0.53f)
    style.setColor(ImGuiCol.ScrollbarGrab, 0.41f, 0.41f, 0.41f, 1.00f)
    style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.52f, 0.52f, 0.52f, 1.00f)
    style.setColor(ImGuiCol.ScrollbarGrabActive, 0.76f, 0.76f, 0.76f, 1.00f)
    style.setColor(ImGuiCol.CheckMark, 0.65f, 0.65f, 0.65f, 1.00f)
    style.setColor(ImGuiCol.SliderGrab, 0.52f, 0.52f, 0.52f, 1.00f)
    style.setColor(ImGuiCol.SliderGrabActive, 0.64f, 0.64f, 0.64f, 1.00f)
    style.setColor(ImGuiCol.Button, 0.54f, 0.54f, 0.54f, 0.35f)
    style.setColor(ImGuiCol.ButtonHovered, 0.52f, 0.52f, 0.52f, 0.59f)
    style.setColor(ImGuiCol.ButtonActive, 0.76f, 0.76f, 0.76f, 1.00f)
    style.setColor(ImGuiCol.Header, 0.38f, 0.38f, 0.38f, 1.00f)
    style.setColor(ImGuiCol.HeaderHovered, 0.47f, 0.47f, 0.47f, 1.00f)
    style.setColor(ImGuiCol.HeaderActive, 0.76f, 0.76f, 0.76f, 0.77f)
    style.setColor(ImGuiCol.Separator, 0.00f, 0.00f, 0.00f, 0.14f)
    style.setColor(ImGuiCol.SeparatorHovered, 0.70f, 0.67f, 0.60f, 0.29f)
    style.setColor(ImGuiCol.SeparatorActive, 0.70f, 0.67f, 0.60f, 0.67f)
    style.setColor(ImGuiCol.ResizeGrip, 0.99f, 0.78f, 0.61f, 1.00f)
    style.setColor(ImGuiCol.ResizeGripHovered, 0.78f, 0.62f, 0.48f, 1.00f)
    style.setColor(ImGuiCol.ResizeGripActive, 0.86f, 0.68f, 0.53f, 1.00f)
    style.setColor(ImGuiCol.Tab, 0.25f, 0.25f, 0.25f, 1.00f)
    style.setColor(ImGuiCol.TabHovered, 0.40f, 0.40f, 0.40f, 1.00f)
    style.setColor(ImGuiCol.TabActive, 0.33f, 0.33f, 0.33f, 1.00f)
    style.setColor(ImGuiCol.TabUnfocused, 0.25f, 0.25f, 0.25f, 1.00f)
    style.setColor(ImGuiCol.TabUnfocusedActive, 0.33f, 0.33f, 0.33f, 1.00f)
    style.setColor(ImGuiCol.DockingPreview, 1.00f, 0.37f, 0.64f, 1.00f)
    style.setColor(ImGuiCol.DockingEmptyBg, 0.75f, 0.28f, 0.47f, 1.00f)
    style.setColor(ImGuiCol.PlotLines, 0.61f, 0.61f, 0.61f, 1.00f)
    style.setColor(ImGuiCol.PlotLinesHovered, 1.00f, 0.72f, 0.30f, 1.00f)
    style.setColor(ImGuiCol.PlotHistogram, 0.68f, 0.84f, 0.51f, 1.00f)
    style.setColor(ImGuiCol.PlotHistogramHovered, 0.48f, 0.59f, 0.42f, 1.00f)
    style.setColor(ImGuiCol.TextSelectedBg, 0.73f, 0.73f, 0.73f, 0.35f)
    style.setColor(ImGuiCol.DragDropTarget, 0.51f, 0.83f, 0.98f, 1.00f)
    style.setColor(ImGuiCol.NavHighlight, 0.38f, 0.62f, 0.73f, 1.00f)
    style.setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 1.00f, 1.00f, 0.70f)
    style.setColor(ImGuiCol.NavWindowingDimBg, 0.80f, 0.80f, 0.80f, 0.20f)
    style.setColor(ImGuiCol.ModalWindowDimBg, 0.80f, 0.80f, 0.80f, 0.35f)

    style.setPopupRounding(3f)
    style.setWindowPadding(2f, 2f)
    style.setFramePadding(4f, 4f)
    style.setItemSpacing(3f, 3f)
    style.setItemInnerSpacing(3f, 3f)
    style.setScrollbarSize(18f)
    style.setWindowBorderSize(1f)
    style.setChildBorderSize(1f)
    style.setPopupBorderSize(1f)
    style.setFrameBorderSize(0f)
    style.setWindowRounding(2f)
    style.setChildRounding(2f)
    style.setFrameRounding(2f)
    style.setScrollbarRounding(2f)
    style.setGrabRounding(3f)
    style.setTabBorderSize(0f)
    style.setTabRounding(3f)
    style.setWindowMenuButtonPosition(ImGuiDir.Right)
