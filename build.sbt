ThisBuild / organization := "Husenap"
ThisBuild / version      := "0.2.0"
ThisBuild / scalaVersion := "3.1.0-RC2"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val sunburstCore = sunburstProject("sunburst-core")
  .settings(
    Dependencies.sunburstCore
  )

lazy val sunburstEditor = sunburstProject("sunburst-editor")
  .settings(
    Compile / run / fork := true
  )
  .dependsOn(sunburstCore)

lazy val example = sunburstProject("sunburst-example")
  .settings(
    Compile / run / fork       := true,
    assembly / assemblyJarName := "example.jar",
    Assembly.strategy
  )
  .dependsOn(sunburstCore)

def sunburstProject(name: String): Project =
  Project(id = name, base = file(name))
    .settings(BuildSettings.sunburstCommonSettings: _*)

lazy val userProjects: Seq[ProjectReference] = List[ProjectReference](
  sunburstCore
)

lazy val nonUserProjects: Seq[ProjectReference] = List[ProjectReference](
  sunburstEditor
)

lazy val sunburst = (project in file("."))
  .settings(BuildSettings.sunburstCommonSettings: _*)
  .aggregate((userProjects ++ nonUserProjects): _*)
