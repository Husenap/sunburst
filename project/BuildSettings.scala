import sbt._
import Keys._

object BuildSettings {

  def sunburstCommonSettings = Def.settings(
    Test / parallelExecution := false,
    // format: off
    Compile / doc / scalacOptions ++= Seq(
      "-groups",
      "-project-version", version.value,
      "-social-links:github::https://github.com/husenap/sunburst",
      "-source-links:github://husenap/sunburst/develop",
      "-siteroot", (ThisBuild / baseDirectory).value.getAbsolutePath()
    )
    // format: on
  )
}
