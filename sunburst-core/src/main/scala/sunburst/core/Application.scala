package sunburst.core

import sunburst.core.window.Window
import sunburst.core.window.WindowOptions
import sunburst.core.input.InputManager

abstract class Application(windowOptions: WindowOptions = WindowOptions()):
  var window: Window             = compiletime.uninitialized
  var inputManager: InputManager = compiletime.uninitialized

  private def getCurrentTime: Float = System.nanoTime() / 1e9f

  final def run() =
    window = Window(windowOptions)
    inputManager = InputManager(window)
    init()

    var currentTime = getCurrentTime
    var accumulator = 0f
    var t           = 0f
    val dt          = tickRate

    while !window.shouldClose do
      val newTime   = getCurrentTime
      val frameTime = (newTime - currentTime) min 0.25f
      currentTime = newTime
      accumulator += frameTime

      window.pollEvents()
      while accumulator >= dt do
        tick(t, dt)
        t += dt
        accumulator -= dt

      val alpha = accumulator / dt

      window.startFrame()
      render(alpha)
      window.endFrame()

      window.swapBuffers()

    window.dispose()

  protected def init(): Unit
  protected def tick(t: Float, dt: Float): Unit
  protected def render(alpha: Float): Unit

  protected def tickRate: Float = 1f / 60f
