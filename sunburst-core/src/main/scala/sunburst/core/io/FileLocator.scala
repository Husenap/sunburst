package sunburst.core.io

import java.nio.file.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import scala.jdk.CollectionConverters.*

object FileLocator:
  private lazy val tmpDir =
    Paths.get(System.getProperty("java.io.tmpdir")).resolve("Sunburst")

  def locateTempFile(filePath: String): Path =
    val name = tmpDir.getFileSystem.getPath(filePath)
    val file = tmpDir.resolve(name)
    Files.createDirectories(file.getParent)
    if !Files.exists(file) then Files.createFile(file)
    file

  def readFileToByteBuffer(filePath: String): ByteBuffer =
    val classloader = Thread.currentThread.getContextClassLoader
    val is          = classloader.getResourceAsStream(filePath)
    val bytes       = is.readAllBytes()
    val data        =
      ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder())
    data.put(bytes)
    data.flip()
    data

  def readFileToString(filePath: String): String =
    val classloader = Thread.currentThread.getContextClassLoader
    val url         = classloader.getResource(filePath)
    assert(url != null, s"File not found: $filePath")
    val bs          = scala.io.Source.fromURL(url)
    bs.getLines.mkString("\n")
