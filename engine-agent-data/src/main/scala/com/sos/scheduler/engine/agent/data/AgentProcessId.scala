package com.sos.scheduler.engine.agent.data

import com.sos.scheduler.engine.data.base.GenericLong
import org.jetbrains.annotations.TestOnly
import scala.math.abs

/**
 * @author Joacim Zschimmer
 */
final case class AgentProcessId(value: Long) extends GenericLong {
  import com.sos.scheduler.engine.agent.data.AgentProcessId._

  def string = value.toString

  /**
   *  Meaningless, just a hint for debugging.
   */
  @TestOnly
  def index = value / Factor
}

object AgentProcessId extends GenericLong.HasJsonFormat[AgentProcessId] {

  private val Factor = 1000*1000*1000L

  def apply(index: Long, salt: Long) = new AgentProcessId(index * Factor + abs(salt) % Factor)
}