import sbt._
import Keys._

object Dependencies {

  object Compile {
    val osNames      = Seq("linux", "windows", "macos")
    val lwjglModules = Seq("lwjgl", "lwjgl-glfw", "lwjgl-opengl", "lwjgl-stb")

    val lwjgl = (for (module <- lwjglModules) yield {
      for (osName <- osNames) yield {
        "org.lwjgl" % s"$module" % "3.2.3" classifier s"natives-$osName"
      }
    }).flatten

    val imgui = Seq(
      "org.lwjgl"       % "lwjgl-stb"         % "3.2.3",
      "io.github.spair" % "imgui-java-lwjgl3" % "1.84.1.0"
    ) ++ osNames.map(osName =>
      "io.github.spair" % s"imgui-java-natives-$osName" % "1.84.1.0"
    )

    object Test {
      val scalatest = "org.scalatest" %% "scalatest" % "3.2.10"
    }
  }

  import Compile._

  val l            = libraryDependencies
  val sunburstCore = l ++= Seq(Test.scalatest) ++ imgui ++ lwjgl
}
