package com.sos.scheduler.engine.agent.client

import akka.actor.ActorSystem
import akka.util.Timeout
import com.sos.scheduler.engine.agent.client.AgentClient._
import com.sos.scheduler.engine.agent.data.commandresponses.{EmptyResponse, FileOrderSourceContent, StartTaskResponse}
import com.sos.scheduler.engine.agent.data.commands._
import com.sos.scheduler.engine.common.scalautil.Logger
import com.sos.scheduler.engine.common.soslicense.LicenseKeyString
import com.sos.scheduler.engine.common.sprayutils.SimpleTypeSprayJsonSupport._
import com.sos.scheduler.engine.common.time.ScalaTime._
import java.time.Duration
import org.scalactic.Requirements._
import scala.collection.immutable
import scala.concurrent.Future
import spray.client.pipelining._
import spray.http.CacheDirectives.{`no-cache`, `no-store`}
import spray.http.HttpHeaders.{Accept, `Cache-Control`}
import spray.http.MediaTypes._
import spray.http.StatusCodes.InternalServerError
import spray.http._
import spray.httpx.SprayJsonSupport._
import spray.httpx.UnsuccessfulResponseException
import spray.httpx.encoding.Gzip
import spray.json.JsBoolean

/**
 * Client for JobScheduler Agent.
 * The HTTP requests are considerd to be responded within `RequestTimeout`.
 * The command [[RequestFileOrderSourceContent]] has an own timeout, which is used for the HTTP request, too (instead of `RequestTimeout`).
 *
 * @author Joacim Zschimmer
 */
trait AgentClient {
  import actorSystem.dispatcher

  protected[client] val agentUri: String
  protected def licenseKeys: immutable.Iterable[LicenseKeyString]
  implicit protected val actorSystem: ActorSystem

  protected lazy val agentUris = AgentUris(agentUri)
  private lazy val addLicenseKeys: RequestTransformer = if (licenseKeys.nonEmpty) addHeader(AgentUris.LicenseKeyHeaderName, licenseKeys mkString " ")
    else identity
  private lazy val nonCachingHttpResponsePipeline: HttpRequest ⇒ Future[HttpResponse] =
    addHeader(Accept(`application/json`)) ~>
      addHeader(`Cache-Control`(`no-cache`, `no-store`)) ~>
      addLicenseKeys ~>
      encode(Gzip) ~>
      sendReceive ~>
      decode(Gzip)

  final def executeCommand(command: Command): Future[command.Response] = {
    logger.debug(s"Execute $command")
    val response = command match {
      case command: RequestFileOrderSourceContent ⇒ executeRequestFileOrderSourceContent(command)
      case command: StartTask ⇒ (nonCachingHttpResponsePipeline ~> unmarshal[StartTaskResponse]).apply(Post(agentUris.command, command: Command))
      case (_: DeleteFile | _: MoveFile | _: SendProcessSignal | _: CloseTask | _: Terminate | AbortImmediately) ⇒
        (nonCachingHttpResponsePipeline ~> unmarshal[EmptyResponse.type]).apply(Post(agentUris.command, command: Command))
    }
    response map { _.asInstanceOf[command.Response] } recover {
      case e: UnsuccessfulResponseException if e.response.status == InternalServerError ⇒
        import e.response.entity
        val message = if (entity.data.length < 1024) entity.asString else entity.data.length + " bytes"
        throw new RuntimeException(s"HTTP-${e.response.status}: $message")
    }
  }

  private def executeRequestFileOrderSourceContent(command: RequestFileOrderSourceContent): Future[FileOrderSourceContent] = {
    val timeout = commandDurationToRequestTimeout(command.duration)
    val pipeline =
      addHeader(Accept(`application/json`)) ~>
        addLicenseKeys ~>
        encode(Gzip) ~>
        sendReceive(actorSystem, actorSystem.dispatcher, timeout) ~>
        decode(Gzip) ~>
        unmarshal[FileOrderSourceContent]
    pipeline(Post(agentUris.command, command: Command))
  }

  final def fileExists(filePath: String): Future[Boolean] =
    (nonCachingHttpResponsePipeline ~> unmarshal[JsBoolean]).apply(Get(Uri(agentUris.fileExists(filePath)))) map { _.value }

  override def toString = s"AgentClient($agentUri)"
}

object AgentClient {
  val RequestTimeout = 60.s
  //private val RequestTimeoutMaximum = Int.MaxValue.ms  // Limit is the number of Akka ticks, where a tick can be as short as 1ms (see akka.actor.LightArrayRevolverScheduler.checkMaxDelay)
  private val logger = Logger(getClass)

  /**
   * The returns timeout for the HTTP request is longer than the expected duration of the request
   */
  private[client] def commandDurationToRequestTimeout(duration: Duration): Timeout = {
    require(duration >= 0.s)
    Timeout((duration + RequestTimeout).toFiniteDuration)
  }
}