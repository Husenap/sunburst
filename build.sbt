ThisBuild / organization := "Husenap"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "3.0.2"

ThisBuild / run / fork := true

Compile / doc / scalacOptions := Seq("-groups", "-implicits")

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

val osNames      = Seq("linux", "windows", "macos")
val lwjglModules = Seq("lwjgl", "lwjgl-glfw", "lwjgl-stb")

lazy val core = (project in file("core"))
  .settings(
    name                                    := "sunburst-core",
    assembly / assemblyJarName              := "sunburst-core.jar",
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

lazy val app = (project in file("app"))
  .settings(
    name                          := "sunburst-app",
    assembly / assemblyJarName    := "sunburst-app.jar",
    Compile / run / baseDirectory := {
      val f = file("sandbox")
      IO.createDirectory(f)
      f
    }
  )
  .dependsOn(core)

lazy val root = (project in file("."))
  .settings(
    name := "sunburst"
  )
  .aggregate(core)
