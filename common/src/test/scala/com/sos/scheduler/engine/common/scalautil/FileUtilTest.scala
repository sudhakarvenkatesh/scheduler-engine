package com.sos.scheduler.engine.common.scalautil

import com.sos.scheduler.engine.common.scalautil.FileUtilTest._
import com.sos.scheduler.engine.common.scalautil.FileUtils.implicits._
import java.io.File
import java.nio.charset.StandardCharsets.{UTF_16BE, UTF_8}
import java.nio.file.{Files, Path}
import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FreeSpec}

/**
 * @author Joacim Zschimmer
 */
@RunWith(classOf[JUnitRunner])
final class FileUtilTest extends FreeSpec with BeforeAndAfterAll {

  private lazy val file = Files.createTempFile("FileUtilTest-", ".tmp").toFile

  override def afterAll(): Unit = {
    Files.delete(file)
  }

  "implicit fileToPath" in {
    new File("/a"): Path
  }

  "implicit pathToFile" in {
    new File("/a").toPath: File
  }

  "slash" in {
    assert(new File("/a") / "b" == new File("/a", "b"))
  }

  "contentString" in {
    file.contentString = TestString
    file.contentString shouldEqual TestString
    new String(Files.readAllBytes(file), UTF_8) shouldEqual TestString
  }

  "contentBytes" in {
    file.contentBytes shouldEqual TestBytes
    file.contentBytes = Array[Byte](1, 2)
    file.contentBytes shouldEqual Vector[Byte](1, 2)
  }

  "write" in {
    file.write(TestString, UTF_16BE)
    file.contentBytes shouldEqual TestString.getBytes(UTF_16BE)
  }

  "append" in {
    file.append("X", UTF_16BE)
    file.contentString(UTF_16BE) shouldEqual TestString + "X"
  }
}

private object FileUtilTest {
  private val TestString = "AÅ"
  private val TestBytes = TestString.getBytes(UTF_8)
  assert(TestBytes.length == 3)
}