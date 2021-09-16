val scala3Version = "3.0.2"

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "macos"
  case n if n.startsWith("Windows") => "windows"
  case _                            => throw new Exception("Unknown platform!!")
}

lazy val sunburst = project
  .in(file("."))
  .settings(
    name := "sunburst",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies += "org.lwjgl" % "lwjgl" % "3.2.3" classifier s"natives-$osName",
    libraryDependencies += "org.lwjgl" % "lwjgl-glfw" % "3.2.3",
    libraryDependencies += "org.lwjgl" % "lwjgl-opengl" % "3.2.3",
    libraryDependencies += "io.github.spair" % "imgui-java-lwjgl3" % "1.84.1.0",
    libraryDependencies += "io.github.spair" % "imgui-java-natives-windows" % "1.84.1.0"
  )
