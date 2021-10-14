import sbt._
import Keys._
import sbtassembly.AssemblyKeys._
import sbtassembly.MergeStrategy
import sbtassembly.PathList

object Assembly {
  val strategy = assemblyMergeStrategy := {
    case PathList("META-INF", _*) => MergeStrategy.discard
    case x                        =>
      val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
      oldStrategy(x)
  }
}
