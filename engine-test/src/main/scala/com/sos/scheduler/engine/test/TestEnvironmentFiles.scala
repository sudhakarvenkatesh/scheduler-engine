package com.sos.scheduler.engine.test

import TestEnvironmentFiles._
import com.sos.scheduler.engine.common.time.ScalaJoda._
import com.sos.scheduler.engine.kernel.util.ResourcePath
import java.io.File
import java.net.URL
import org.joda.time.Instant.now
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

final class TestEnvironmentFiles(
    configResourcePath: ResourcePath,
    directory: File,
    nameMap: Map[String, String],
    fileTransformer: ResourceToFileTransformer) {

  private val resolver = new PathMatchingResourcePatternResolver
  private val lastModified = now() - 3.s

  private def copy(): Unit = {
    for ((name, url) <- resourceUrls) copyResource(url, name)
  }

  private def resourceUrls: Iterable[(String, URL)] =
    resourceUrls(defaultConfigResourcePath) ++ resourceUrls(configResourcePath) map { o => nameOfUrl(o) -> o }

  private def resourceUrls(p: ResourcePath): Iterable[URL] =
    resources(p) map { _.getURL }

  private def resources(p: ResourcePath): Iterable[Resource] =
    ResourcePatterns flatMap { o ⇒ resources(p, o) }

  private def resources(p: ResourcePath, namePattern: String): Iterable[Resource] =
    resolver.getResources(s"classpath*:${p.path}/$namePattern")

  private def copyResource(url: URL, name: String): Unit = {
    val f = new File(directory, name)
    fileTransformer.transform(url, f)
    f.setLastModified(lastModified.getMillis)
  }

  private def nameOfUrl(u: URL): String = {
    val name = new File(u.getPath).getName
    nameMap.getOrElse(name, name)
  }
}

object TestEnvironmentFiles {
  private val ResourcePatterns = List("*.xml", "*.ini", "*.dtd")

  def copy(
      configResourcePath: ResourcePath,
      directory: File,
      nameMap: Map[String, String] = Map(),
      fileTransformer: ResourceToFileTransformer = StandardResourceToFileTransformer.singleton): Unit = {
    new TestEnvironmentFiles(configResourcePath, directory, nameMap, fileTransformer).copy()
  }

  private val defaultConfigResourcePath: ResourcePath = new ResourcePath(classOf[TestEnvironmentFiles].getPackage, "config")
}
