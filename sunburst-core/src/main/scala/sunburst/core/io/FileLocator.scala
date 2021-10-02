package sunburst.core.io

import java.nio.file.*

object FileLocator:
  private lazy val tmpDir =
    Paths.get(System.getProperty("java.io.tmpdir")).resolve("Sunburst")

  def locateTempFile(filePath: String): Path =
    val name = tmpDir.getFileSystem.getPath(filePath)
    val file = tmpDir.resolve(name)
    Files.createDirectories(file.getParent)
    if !Files.exists(file) then Files.createFile(file)
    file
