ThisBuild / scalaVersion := "3.0.2"
ThisBuild / organization := "com.husseintaher"
ThisBuild / version := "0.1.0"

ThisBuild / run / fork := true

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "macos"
  case n if n.startsWith("Windows") => "windows"
  case _                            => throw new Exception("Unknown platform!!")
}

lazy val window = (project in file("window"))
  .settings(
    libraryDependencies += "org.lwjgl" % "lwjgl" % "3.2.3" classifier s"natives-$osName",
    libraryDependencies += "org.lwjgl" % "lwjgl-glfw" % "3.2.3",
    libraryDependencies += "org.lwjgl" % "lwjgl-opengl" % "3.2.3",
    libraryDependencies += "io.github.spair" % "imgui-java-lwjgl3" % "1.84.1.0",
    libraryDependencies += "io.github.spair" % s"imgui-java-natives-$osName" % "1.84.1.0"
  )

lazy val app = (project in file("app"))
  .settings(
    Compile / run / baseDirectory := {
      val f = file("bin")
      IO.createDirectory(f)
      f
    }
  )
  .dependsOn(window)
