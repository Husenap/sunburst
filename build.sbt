ThisBuild / organization := "Husenap"
ThisBuild / version      := "0.2.0"
ThisBuild / scalaVersion := "3.0.2"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val osNames      = Seq("linux", "windows", "macos")
lazy val lwjglModules = Seq("lwjgl", "lwjgl-glfw", "lwjgl-stb")

lazy val dependencies = Seq(
  libraryDependencies ++= lwjglModules
    .map(module =>
      osNames.map(osName =>
        "org.lwjgl" % s"$module" % "3.2.3" classifier s"natives-$osName"
      )
    )
    .flatten,
  libraryDependencies ++= osNames.map(osName =>
    "io.github.spair" % s"imgui-java-natives-$osName" % "1.84.1.0"
  ),
  libraryDependencies += "org.lwjgl"       % "lwjgl-opengl"      % "3.2.3",
  libraryDependencies += "org.lwjgl"       % "lwjgl-stb"         % "3.2.3",
  libraryDependencies += "io.github.spair" % "imgui-java-lwjgl3" % "1.84.1.0"
)

lazy val core = (project in file("."))
  .settings(dependencies)
  .settings(
    name := "sunburst"
  )

lazy val example = (project in file("example"))
  .settings(
    name                          := "sunburst-example",
    Compile / run / fork          := true,
    Compile / run / baseDirectory := {
      val f = file("sandbox")
      IO.createDirectory(f)
      f
    }
  )
  .dependsOn(core)

// format: off
Compile / doc / scalacOptions ++= Seq(
  "-groups",
  "-project-version", version.value,
  "-social-links:github::https://github.com/husenap/sunburst",
  "-source-links:github://husenap/sunburst/develop",
  "-siteroot", "."
)
// format: on
