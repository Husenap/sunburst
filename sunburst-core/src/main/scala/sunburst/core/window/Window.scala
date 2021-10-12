package sunburst.core.window

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
import sunburst.core.event.EventEmitter
import sunburst.core.graphics.framework.*

import java.nio.ByteBuffer
import java.nio.file.Paths

case class WindowOptions(
    val title: String = "Sunburst",
    val width: Int = 1600,
    val height: Int = 900,
    val vSync: Boolean = true
)

class Window private (options: WindowOptions) extends EventEmitter[WindowEvent]:
  private var glslVersion: String = ""
  private var _handle: Long       = 0

  private def init() =
    initWindow()
    ImGuiWrapper.init(_handle, glslVersion)

  private def initWindow() =
    GLFWErrorCallback.createPrint(System.err).set
    if !glfwInit() then throw IllegalStateException("Unable to initialize GLFW")

    decideGlslVersion()

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    _handle =
      glfwCreateWindow(options.width, options.height, options.title, NULL, NULL)
    if _handle == NULL then
      throw RuntimeException("Failed to create GLFW window")

    glfwMakeContextCurrent(_handle)
    GL.createCapabilities()
    glfwSwapInterval(if options.vSync then 1 else 0)

    glfwShowWindow(_handle)

    registerCallbacks()

  def dispose() =
    ImGuiWrapper.dispose()
    disposeWindow()

  private def disposeWindow() =
    glfwFreeCallbacks(_handle)
    glfwDestroyWindow(_handle)
    glfwTerminate()
    glfwSetErrorCallback(null).free()

  private def decideGlslVersion() =
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

  def registerCallbacks(): Unit =
    glfwSetFramebufferSizeCallback(
      _handle,
      (_, width, height) => broadcast(WindowEvent.Resize(width, height))
    )
    glfwSetKeyCallback(
      _handle,
      (_, key, scancode, action, mods) =>
        val ac = Action.fromOrdinal(action)
        broadcast(
          WindowEvent.Key(key, scancode, ac, mods)
        )
        ac match
          case Action.Release =>
            broadcast(WindowEvent.KeyRelease(key, scancode, mods))
          case Action.Press   =>
            broadcast(WindowEvent.KeyPress(key, scancode, mods))
          case Action.Repeat  =>
            broadcast(WindowEvent.KeyRepeat(key, scancode, mods))
    )
    glfwSetDropCallback(
      _handle,
      (_, count, paths) =>
        for i <- 0 until count do
          broadcast(WindowEvent.DroppedFile(GLFWDropCallback.getName(paths, i)))
    )
    glfwSetWindowContentScaleCallback(
      _handle,
      (_, scaleX, scaleY) => broadcast(WindowEvent.ContentScale(scaleX, scaleY))
    )
    glfwSetCharCallback(
      _handle,
      (_, codepoint) => broadcast(WindowEvent.Char(codepoint.toChar))
    )
    glfwSetCursorPosCallback(
      _handle,
      (_, posX, posY) => broadcast(WindowEvent.CursorPos(posX, posY))
    )
    glfwSetCursorEnterCallback(
      _handle,
      (_, entered) =>
        broadcast(
          if entered then WindowEvent.CursorEnter
          else WindowEvent.CursorLeave
        )
    )
    glfwSetMouseButtonCallback(
      _handle,
      (_, button, action, mods) =>
        val ac  = Action.fromOrdinal(action)
        val btn = Button.fromOrdinal(button)
        broadcast(WindowEvent.MouseButton(btn, ac, mods))
        ac match
          case Action.Press   =>
            broadcast(WindowEvent.MouseButtonPress(btn, mods))
          case Action.Release =>
            broadcast(WindowEvent.MouseButtonRelease(btn, mods))
          case _              => ()
    )
    glfwSetScrollCallback(
      _handle,
      (_, offsetX, offsetY) => broadcast(WindowEvent.Scroll(offsetX, offsetY))
    )

object Window:
  def apply(options: WindowOptions = WindowOptions()): Window =
    val window = new Window(options)
    window.init()
    window
