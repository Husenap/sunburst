package sunburst

import sunburst.window.Window

class Application:
  lazy val window = Window()

  def run() =
    window.init()
    init()

    while !window.shouldClose do
      window.pollEvents()
      window.startFrame()

      tick()

      window.endFrame()
      window.swapBuffers()

    window.dispose()

  protected def init(): Unit = ???
  protected def tick(): Unit = ???
