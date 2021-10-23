import Util._

addCommandAlias("ls", "projects")
addCommandAlias("cd", "project")
addCommandAlias("c", "compile")
addCommandAlias("cl", "clean")
addCommandAlias("ca", "Test / compile")
addCommandAlias("t", "test")
addCommandAlias("r", "run")
addCommandAlias("re", "reload")
addCommandAlias("asm", "assembly")
addCommandAlias(
  "styleCheck",
  "scalafmtSbtCheck; scalafmtCheckAll; Test / compile; scalafixAll --check"
)
addCommandAlias(
  "styleFix",
  "Test / compile; scalafixAll; scalafmtSbt; scalafmtAll"
)

ThisBuild / onLoadMessage :=
  s"""|
      |#=================================#
      ||     List of defined ${styled("aliases")}     |
      |#=================================#
      || ${styled("ls")}          | projects          |
      || ${styled("cd")}          | project           |
      || ${styled("c")}           | compile           |
      || ${styled("ca")}          | compile all       |
      || ${styled("cl")}          | clean             |
      || ${styled("t")}           | test              |
      || ${styled("r")}           | run               |
      || ${styled("re")}          | reload            |
      || ${styled("styleCheck")}  | fmt & fix checks  |
      || ${styled("styleFix")}    | fix then fmt      |
      || ${styled("asm")}         | assembly          |
      |#=================================#""".stripMargin
