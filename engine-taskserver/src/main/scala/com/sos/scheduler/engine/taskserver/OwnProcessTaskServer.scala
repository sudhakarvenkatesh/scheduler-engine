package com.sos.scheduler.engine.taskserver

import com.google.common.io.Closer
import com.sos.scheduler.engine.base.process.ProcessSignal
import com.sos.scheduler.engine.common.scalautil.AutoClosing._
import com.sos.scheduler.engine.common.scalautil.Closers.implicits.RichClosersCloser
import com.sos.scheduler.engine.common.scalautil.Logger
import com.sos.scheduler.engine.taskserver.OwnProcessTaskServer._
import com.sos.scheduler.engine.taskserver.data.TaskStartArguments
import com.sos.scheduler.engine.taskserver.task.process.{JavaProcess, ProcessConfiguration, RichProcess}
import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import spray.json._

/**
 * @author Joacim Zschimmer
 */
final class OwnProcessTaskServer(val taskStartArguments: TaskStartArguments, javaOptions: Seq[String], javaClasspath: String)
extends TaskServer {

  private var process: RichProcess = null
  private val terminatedPromise = Promise[Unit]()
  def terminated = terminatedPromise.future

  def start() = {
    val stdFileMap = RichProcess.createTemporaryStdFiles()
    val closer = Closer.create()
    closeOnError(closer) {
      closer.onClose { RichProcess.tryDeleteFiles(stdFileMap.values) }
      process = JavaProcess.startJava(
        ProcessConfiguration(
          stdFileMap,
          additionalEnvironment = taskStartArguments.environment,
          idStringOption = Some(taskStartArguments.agentTaskId.string),
          killScriptFileOption = taskStartArguments.killScriptFileOption),
        options = javaOptions,
        classpath = Some(javaClasspath + File.pathSeparator + JavaProcess.OwnClasspath),
        mainClass = TaskServerMain.getClass.getName stripSuffix "$", // Strip Scala object class suffix
        arguments = Nil)
      process.closed.onComplete { tried ⇒
        for (t ← tried.failed) logger.error(t.toString, t)
        try closer.close()
        finally terminatedPromise.complete(tried)
      }
      val a = taskStartArguments.copy(stdFileMap = stdFileMap, logStdoutAndStderr = true)
      process.stdinWriter.write(a.toJson.compactPrint)
      process.stdinWriter.close()
    }
  }

  override def close(): Unit = {
    if (process != null) {
      // Wait for process _after_ Tunnel, registered with registerCloseable, has been closed
      try process.waitForTermination()
      finally process.close()
    }
  }

  def sendProcessSignal(signal: ProcessSignal) =
    for (p ← Option(process)) p.sendProcessSignal(signal)
}

object OwnProcessTaskServer {
  private val logger = Logger(getClass)
}