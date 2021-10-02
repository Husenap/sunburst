import sunburst._

ThisBuild / organization := "Husenap"
ThisBuild / version      := "0.2.0"
ThisBuild / scalaVersion := "3.0.2"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val userProjects: Seq[ProjectReference] = List[ProjectReference](
  sunburstCore
)

lazy val aggregatedProjects: Seq[ProjectReference] =
  userProjects ++ List[ProjectReference](
    sunburstEditor
  )

lazy val root = (project in file("."))
  .aggregate(aggregatedProjects: _*)
  .settings(
    name := "sunburst"
  )

lazy val sunburstCore = (project in file("sunburst-core"))
  .settings(Dependencies.sunburstCore)

lazy val sunburstEditor = (project in file("sunburst-editor"))
  .dependsOn(sunburstCore)
  .settings(
    Compile / run / fork := true
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
  .dependsOn(sunburstCore)

// format: off
Compile / doc / scalacOptions ++= Seq(
  "-groups",
  "-project-version", version.value,
  "-social-links:github::https://github.com/husenap/sunburst",
  "-source-links:github://husenap/sunburst/develop",
  "-siteroot", "."
)
// format: on
