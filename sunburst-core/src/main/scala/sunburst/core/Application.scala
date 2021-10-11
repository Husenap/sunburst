package sunburst.core

import sunburst.core.window.Window

abstract class Application:
  lazy val window = Window()

  private def getCurrentTime: Float = System.nanoTime() / 1e9f

  final def run() =
    window.init()
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

      while accumulator >= dt do
        window.pollEvents()
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
