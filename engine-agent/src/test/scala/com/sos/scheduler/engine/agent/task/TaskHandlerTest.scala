package com.sos.scheduler.engine.agent.task

import akka.actor.{Actor, ActorSystem, Props}
import com.google.inject.{AbstractModule, Guice, Provides}
import com.sos.scheduler.engine.agent.data.AgentTaskId
import com.sos.scheduler.engine.agent.data.commandresponses.{EmptyResponse, StartTaskResponse}
import com.sos.scheduler.engine.agent.data.commands._
import com.sos.scheduler.engine.agent.task.TaskHandlerTest._
import com.sos.scheduler.engine.base.exceptions.PublicException
import com.sos.scheduler.engine.base.process.ProcessSignal
import com.sos.scheduler.engine.base.process.ProcessSignal.{SIGKILL, SIGTERM}
import com.sos.scheduler.engine.common.guice.GuiceImplicits._
import com.sos.scheduler.engine.common.scalautil.Collections.implicits.RichTraversable
import com.sos.scheduler.engine.common.scalautil.Futures._
import com.sos.scheduler.engine.common.soslicense.{LicenseKey, LicenseKeyParameterIsMissingException}
import com.sos.scheduler.engine.common.system.OperatingSystem._
import com.sos.scheduler.engine.common.time.ScalaTime._
import com.sos.scheduler.engine.taskserver.TaskServer
import com.sos.scheduler.engine.taskserver.data.TaskStartArguments
import com.sos.scheduler.engine.tunnel.core.TunnelClient
import com.sos.scheduler.engine.tunnel.data.{TunnelId, TunnelToken}
import javax.inject.Singleton
import org.junit.runner.RunWith
import org.scalatest.FreeSpec
import org.scalatest.Inside.inside
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner
import scala.concurrent.Promise

/**
 * @author Joacim Zschimmer
 */
@RunWith(classOf[JUnitRunner])
final class TaskHandlerTest extends FreeSpec {

  "Second StartApiTask without a license is rejected - JS-1482" in {
    val testContext = new TestContext
    import testContext.{taskHandler, tasks}
    def startTask() = awaitResult(taskHandler.execute(TestStartApiTask, licenseKey = None), 3.s)
    startTask()
    intercept[LicenseKeyParameterIsMissingException] { startTask() }
    awaitResult(taskHandler.execute(CloseTask(tasks(0).id, kill = false)), 3.s)
    startTask()
  }

  "Start and close task cycle" - {
    lazy val testContext = new TestContext
    import testContext.{taskHandler, taskServers, tasks}

    "StartApiTask" in {
      assert(!taskHandler.terminated.isCompleted)
      for (nextAgentTaskId ← AgentTaskIds) {
        val response = awaitResult(taskHandler.execute(TestStartApiTask, Some(TestLicenseKey)), 3.s)
        inside(response) { case StartTaskResponse(id, TestTunnelToken) ⇒ id shouldEqual nextAgentTaskId }
      }
      for (o ← taskServers) {
        assert(o.started)
        assert(!o.sigkilled)
        assert(!o.sigtermed)
        assert(!o.closed)
      }
    }

    "TaskHandlerView" - {
      def view: TaskHandlerView = taskHandler

      "currentTaskCount" in {
        assert(view.currentTaskCount == taskServers.size)
      }

      "totalTaskCount" in {
        assert(view.totalTaskCount == taskServers.size)
      }

      "tasks" in {
        val taskMap = view.tasks toKeyedMap {_.id}
        assert(taskMap.size == taskServers.size)
        for (id ← AgentTaskIds) assert(taskMap contains id)
        for (o ← taskMap.values) assert(o.masterAddress == TestMasterAddress)
      }
    }

    "CloseTask" in {
      val commands = List(
        CloseTask(tasks(0).id, kill = false),
        CloseTask(tasks(1).id, kill = true))
      for (command ← commands) {
        val response = awaitResult(taskHandler.execute(command), 3.s)
        inside(response) { case EmptyResponse ⇒ }
      }
      assert(taskServers(0).started)
      assert(!taskServers(0).sigkilled)
      assert(!taskServers(0).sigtermed)
      assert(taskServers(0).closed)

      assert(taskServers(1).started)
      assert(taskServers(1).sigkilled)
      assert(!taskServers(1).sigtermed)
      assert(taskServers(1).closed)
      assert(!taskHandler.terminated.isCompleted)
    }

    "TaskHandlerView, after tasks are closed" - {
      def view: TaskHandlerView = taskHandler

      "currentTaskCount" in {
        assert(view.currentTaskCount == 0)
      }

      "totalTaskCount" in {
        assert(view.totalTaskCount == taskServers.size)
      }

      "tasks" in {
        assert(view.tasks.isEmpty)
      }
    }
  }

  "Terminate" - {
    "When no tasks are registered, TaskHandler terminates immediately" in {
      val testContext = new TestContext
      import testContext.taskHandler
      assert(!taskHandler.terminated.isCompleted)
      awaitResult(taskHandler.execute(Terminate(sigtermProcesses = false)), 3.s)
      awaitResult(taskHandler.terminated, 3.s)
    }

    "When a process is registered, TaskHandler terminates after the task has terminated" in {
      val testContext = new TestContext
      import testContext.{taskHandler, taskServers, tasks}
      for (_ ← tasks) awaitResult(taskHandler.execute(TestStartApiTask, Some(TestLicenseKey)), 3.s)
      assert(taskHandler.totalTaskCount == tasks.size)
      assert(taskHandler.currentTaskCount == tasks.size)
      assert(!taskHandler.isTerminating)
      assert(!taskHandler.terminated.isCompleted)
      for (o ← taskServers) assert(!o.sigtermed)
      awaitResult(taskHandler.execute(Terminate(sigtermProcesses = true, sigkillProcessesAfter = Some(2.s))), 3.s)
      intercept[PublicException] { awaitResult(taskHandler.execute(TestStartApiTask, Some(TestLicenseKey)), 3.s) }
      assert(taskHandler.isTerminating)
      for (o ← taskServers) assert(o.sigtermed == !isWindows)
      sleep(1.s)
      assert(!taskHandler.terminated.isCompleted)
      // Now, (mocked) tasks are terminated with SIGKILL
      awaitResult(taskHandler.terminated, 3.s)
      assert(taskHandler.currentTaskCount == tasks.size)   // Because no CloseTask was issued
    }
  }
}

private object TaskHandlerTest {
  private val AgentTaskIds = List("1-1", "2-2") map AgentTaskId.apply
  private val JavaOptions = "JAVA-OPTIONS"
  private val JavaClasspath = "JAVA-CLASSPATH"
  private val TestMasterPort = 9999
  private val TestMasterAddress = s"127.0.0.1:$TestMasterPort"
  private val TestStartApiTask = StartApiTask(javaOptions = JavaOptions, javaClasspath = JavaClasspath)
  private val TestLicenseKey = LicenseKey("SOS-DEMO-1-D3Q-1AWS-ZZ-ITOT9Q6")
  private val TestTunnelToken = TunnelToken(TunnelId("1"), TunnelToken.Secret("SECRET"))

  private class TestContext {
    val taskServers = List.fill(AgentTaskIds.size) { new MockTaskServer }
    val tasks = AgentTaskIds zip taskServers map { case (id, taskServer) ⇒ new AgentTask(id, tunnel = mockTunnelClient(), taskServer) }
    val taskHandler = Guice.createInjector(new TestModule(tasks)).instance[TaskHandler]
  }

  private def mockTunnelClient() = new TunnelClient(
    connectorHandler = ActorSystem().actorOf(Props { new Actor { def receive = { case _ ⇒ }}}),
    TestTunnelToken,
    connected = Promise().future,
    peerAddress = () ⇒ None)

  private class MockTaskServer extends TaskServer {
    private val terminatedPromise = Promise[Unit]()
    var sigtermed = false
    var sigkilled = false
    var started = false
    var closed = false

    def terminated = terminatedPromise.future

    def taskStartArguments = TaskStartArguments.forTest(tcpPort = TestMasterPort)   // For TaskHandler.overview

    def sendProcessSignal(signal: ProcessSignal) = signal match {
      case SIGTERM ⇒ sigtermed = true
      case SIGKILL ⇒ sigkilled = true; terminatedPromise.success(())
    }

    def start() = {
      assert(!started && !closed)
      started = true
    }

    def close() = {
      assert(started && !closed)
      closed = true
    }
  }

  private class TestModule(tasks: List[AgentTask]) extends AbstractModule {
    private val taskIterator = tasks.iterator

    def configure() = {}

    @Provides @Singleton
    private def agentTaskFactory: AgentTaskFactory = new AgentTaskFactory {
      def apply(command: StartTask) = {
        inside(command) {
          case command: StartApiTask ⇒
            command.javaOptions shouldEqual JavaOptions
            command.javaClasspath shouldEqual JavaClasspath
        }
        taskIterator.synchronized { taskIterator.next() }
      }
    }

    @Provides @Singleton
    private def newAgentTaskId: () ⇒ AgentTaskId =
      AgentTaskIds.synchronized { AgentTaskIds.iterator.next }
  }
}