package com.sos.scheduler.engine.data.folder

import com.sos.scheduler.engine.data.filebased.{TypedPath, UnknownTypedPath}
import com.sos.scheduler.engine.data.folder.FolderPathTest._
import com.sos.scheduler.engine.data.job.JobPath
import org.junit.runner.RunWith
import org.scalatest.FreeSpec
import org.scalatest.junit.JUnitRunner

/**
  * @author Joacim Zschimmer
  */
@RunWith(classOf[JUnitRunner])
final class FolderPathTest extends FreeSpec {

  "subfolder" in {
    assert(FolderPath("/").subfolder("x") == FolderPath("/x"))
    assert(FolderPath("/a").subfolder("x") == FolderPath("/a/x"))
    assert(FolderPath("/a/b").subfolder("x") == FolderPath("/a/b/x"))
    intercept[IllegalArgumentException] { FolderPath("/") subfolder "/" }
    intercept[IllegalArgumentException] { FolderPath("/") subfolder "/x" }
    intercept[IllegalArgumentException] { FolderPath("/") subfolder "x/" }
    intercept[IllegalArgumentException] { FolderPath("/") subfolder "x/y" }
  }

  "isParentOf" in {
    assert(FolderPath.Root.isParentOf(FolderPath("/a")))
    assert(!FolderPath.Root.isParentOf(FolderPath.Root))
    assert(!FolderPath("/a").isParentOf(FolderPath("/a")))
    assert(!FolderPath("/a").isParentOf(JobPath("/x")))
    assert(!FolderPath("/a/b").isParentOf(JobPath("/a")))
    assert(!FolderPath("/a/b").isParentOf(JobPath("/a/b")))
    assert(FolderPath("/").isParentOf(JobPath("/x")))
    assert(FolderPath("/a/b").isParentOf(JobPath("/a/b/c")))
    assert(!FolderPath("/a/b").isParentOf(JobPath("/a/b/c/d")))
  }

  "isAncestorOf" in {
    assert(FolderPath.Root isAncestorOf FolderPath.Root)
    assert(FolderPath("/a") isAncestorOf FolderPath("/a"))
    assert(!FolderPath("/a").isAncestorOf(JobPath("/x")))
    assert(!FolderPath("/a/b").isAncestorOf(JobPath("/a")))
    assert(!FolderPath("/a/b").isAncestorOf(JobPath("/a/b")))
    assert(FolderPath("/") isAncestorOf JobPath("/x"))
    assert(FolderPath("/a/b") isAncestorOf JobPath("/a/b/c"))
    assert(FolderPath("/a/b") isAncestorOf JobPath("/a/b/c/d"))
  }

  "fromTrailingSlash" in {
    intercept[IllegalArgumentException] { FolderPath.fromTrailingSlash("") }
    intercept[IllegalArgumentException] { FolderPath.fromTrailingSlash("/a") }
    assert(FolderPath.fromTrailingSlash("/") == FolderPath.Root)
    assert(FolderPath.fromTrailingSlash("/a/") == FolderPath("/a"))
  }

  "resolve" in {
    assert(FolderPath("/default").resolve[TestPath]("a") == TestPath("/default/a"))
    assert(FolderPath("/default").resolve[TestPath]("/a") == TestPath("/a"))
    assert(FolderPath("/default").resolve[TestPath]("./a") == TestPath("/default/a"))
    assert(FolderPath.Root.resolve[TestPath]("./a") == TestPath("/a"))
    assert(FolderPath("/default/x").resolve[TestPath]("/a/b") == TestPath("/a/b"))
    assert(FolderPath("/default/x").resolve[TestPath]("a/b") == TestPath("/default/x/a/b"))
    assert(FolderPath("/default/x").resolve[TestPath]("./a/b") == TestPath("/default/x/a/b"))
    intercept[Exception] { FolderPath("").resolve[TestPath](")./a") }
    intercept[Exception] { FolderPath("x").resolve[TestPath]("./a") }
  }

  "name" in {
    assert(FolderPath("/").name == "")
  }

  "nesting" in {
    assert(FolderPath("/").nesting == 0)
  }

  "withoutStartingSlash" in {
    assert(FolderPath("/").withoutStartingSlash == "")
  }

  "withTrailingSlash" in {
    assert(FolderPath("/").withTrailingSlash == "/")
  }

  "FolderPath.parentOf" in {
    assert(FolderPath.parentOf(UnknownTypedPath("/a")) == FolderPath.Root)
    assert(FolderPath.parentOf(UnknownTypedPath("/folder/a")) == FolderPath("/folder"))
    assert(FolderPath.parentOf(UnknownTypedPath("/x/folder/a")) == FolderPath("/x/folder"))
    intercept[IllegalStateException] { FolderPath.parentOf(FolderPath("/")) }
  }
}

private object FolderPathTest {

  private case class TestPath(string: String) extends TypedPath {
    validate()
    def companion = TestPath
  }

  private object TestPath extends TypedPath.Companion[TestPath]
}
