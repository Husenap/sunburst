package sunburst.window

import imgui.*
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiConfigFlags
import imgui.flag.ImGuiDir
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
import sunburst.graphics.framework.*

import java.nio.ByteBuffer
import java.nio.file.Paths

class Window:
  private var glslVersion: String = ""
  private var _handle: Long       = 0

  def init() =
    initWindow()
    ImGuiWrapper.init(_handle, glslVersion)

  def dispose() =
    ImGuiWrapper.dispose()
    disposeWindow()

  def initWindow() =
    GLFWErrorCallback.createPrint(System.err).set
    if !glfwInit() then throw IllegalStateException("Unable to initialize GLFW")

    decideGlslVersion()

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    _handle = glfwCreateWindow(1600, 900, "Sunburst", NULL, NULL)
    if _handle == NULL then
      throw RuntimeException("Failed to create GLFW window")

    glfwMakeContextCurrent(_handle)
    GL.createCapabilities()
    glfwSwapInterval(1)

    glfwShowWindow(_handle)

  private def disposeWindow() =
    glfwFreeCallbacks(_handle)
    glfwDestroyWindow(_handle)
    glfwTerminate()
    glfwSetErrorCallback(null).free()

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

  def shouldClose: Boolean = glfwWindowShouldClose(_handle)

  def startFrame(): Unit =
    ImGuiWrapper.newFrame()

  def endFrame(): Unit =
    ImGuiWrapper.render()

  def pollEvents(): Unit  = glfwPollEvents()
  def swapBuffers(): Unit = glfwSwapBuffers(_handle)
