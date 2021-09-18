import imgui.*
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import org.lwjgl.*
import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.*
import org.lwjgl.stb.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.*

import java.nio.ByteBuffer
import java.nio.file.Paths

class Window:
  var glslVersion: String = ""
  var handle: Long = 0

  val imGuiGlfw = ImGuiImplGlfw()
  val imGuiGl3 = ImGuiImplGl3()

  val texture = Array(0)

  def init() =
    initWindow()
    initImGui()
    imGuiGlfw.init(handle, true)
    imGuiGl3.init(glslVersion)

    val classloader = Thread.currentThread.getContextClassLoader
    val is = classloader.getResourceAsStream("breakthrough.png")
    val bytes = is.readAllBytes
    val bytebuffer = BufferUtils.createByteBuffer(bytes.length)
    bytebuffer.put(bytes)
    bytebuffer.flip()

    val x = Array(0)
    val y = Array(0)
    val n = Array(0)
    val pixels = STBImage.stbi_load_from_memory(
      bytebuffer,
      x,
      y,
      n,
      4
    )

    if pixels == null then println("failed")
    else println("success")
    println(STBImage.stbi_failure_reason())
    println(s"${x(0)}x${y(0)} with ${n(0)} channels")

    glGenTextures(texture)
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, texture(0))

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    glTexImage2D(
      GL_TEXTURE_2D,
      0,
      GL_RGBA,
      x(0),
      y(0),
      0,
      GL_RGBA,
      GL_UNSIGNED_BYTE,
      pixels
    )
    glGenerateMipmap(GL_TEXTURE_2D)

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

    if ImGui.begin("hejsan") then
      ImGui.labelText("hejsan", "mannen")
      ImGui.image(texture(0), 400f, 400f)
    ImGui.end()

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
