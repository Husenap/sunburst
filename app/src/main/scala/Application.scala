class Application:
  val window = Window()
  def run() =
    window.init()
    window.run()
    window.dispose()

@main def main: Unit =
  try Application().run()
  catch case e: Exception => e.printStackTrace()
