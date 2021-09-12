val scala3Version = "3.0.2"

lazy val sunburst = project
  .in(file("."))
  .settings(
    name := "sunburst",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies += "org.lwjgl" % "lwjgl" % "3.2.3" classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
    libraryDependencies += "org.lwjgl" % "lwjgl-glfw" % "3.2.3",
    libraryDependencies += "org.lwjgl" % "lwjgl-opengl" % "3.2.3",
    libraryDependencies += "io.github.spair" % "imgui-java-lwjgl3" % "1.84.1.0",
    libraryDependencies += "io.github.spair" % "imgui-java-natives-windows" % "1.84.1.0"
  )
