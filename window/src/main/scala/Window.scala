import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._

import imgui._
import imgui.glfw.ImGuiImplGlfw
import imgui.gl3.ImGuiImplGl3
import imgui.flag.ImGuiConfigFlags

class Window:
  var glslVersion: String = ""
  var handle: Long = 0

  val imGuiGlfw = ImGuiImplGlfw()
  val imGuiGl3 = ImGuiImplGl3()

  def init() =
    initWindow()
    initImGui()
    imGuiGlfw.init(handle, true)
    imGuiGl3.init(glslVersion)

  def dispose() =
    imGuiGl3.dispose
    imGuiGlfw.dispose
    disposeImGui()
    disposeWindow()

  def initWindow() =
    GLFWErrorCallback.createPrint(System.err).set
    if !glfwInit then throw IllegalStateException("Unable to initialize GLFW")

    decideGlslVersion()

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    handle = glfwCreateWindow(800, 600, "Sunburst", NULL, NULL)
    if handle == NULL then
      throw RuntimeException("Failed to create GLFW window")

    glfwMakeContextCurrent(handle)
    GL.createCapabilities
    glfwSwapInterval(1)

    glfwShowWindow(handle)

    clearBuffer()
    renderBuffer()

  def initImGui() =
    ImGui.createContext
    ImGui.getIO.setConfigFlags(ImGuiConfigFlags.ViewportsEnable)

  def disposeWindow() =
    glfwFreeCallbacks(handle)
    glfwDestroyWindow(handle)
    glfwTerminate
    glfwSetErrorCallback(null).free

  def disposeImGui() = ImGui.destroyContext

  def decideGlslVersion() =
    val isMac = System.getProperty("os.name").toLowerCase.contains("mac")
    if isMac then
      glslVersion = "#version 150"
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
      glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
    else
      glslVersion = "#version 130"
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0)

  def run() =
    while !glfwWindowShouldClose(handle) do runFrame()

  def runFrame() =
    startFrame()
    process()
    endFrame()

  def clearBuffer() =
    glClearColor(0f, 0f, 0f, 1f)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

  def startFrame() =
    clearBuffer()
    imGuiGlfw.newFrame
    ImGui.newFrame

  def process() =
    ImGui.showDemoWindow()

  def endFrame() =
    ImGui.render
    imGuiGl3.renderDrawData(ImGui.getDrawData)

    if ImGui.getIO.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable) then
      val backupWindowHandle = glfwGetCurrentContext
      ImGui.updatePlatformWindows
      ImGui.renderPlatformWindowsDefault
      glfwMakeContextCurrent(backupWindowHandle)

    renderBuffer()

  def renderBuffer() =
    glfwSwapBuffers(handle)
    glfwPollEvents
