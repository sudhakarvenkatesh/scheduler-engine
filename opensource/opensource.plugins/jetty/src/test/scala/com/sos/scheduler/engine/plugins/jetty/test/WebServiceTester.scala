package com.sos.scheduler.engine.plugins.jetty.test

import com.google.inject.Injector
import com.sos.scheduler.engine.plugins.jetty.WebServer
import com.sos.scheduler.engine.plugins.jetty.configuration.JettyConfiguration
import com.sos.scheduler.engine.plugins.jetty.configuration.JettyConfiguration.TcpPortNumber
import com.sos.scheduler.engine.plugins.jetty.test.JettyPluginTests._
import java.net.URI

final class WebServiceTester(injector: Injector) {

  lazy val jettyConfiguration = JettyConfiguration(portOption = Some(TcpPortNumber.random()))
  lazy val portNumber = jettyConfiguration.portOption.get.value

  private lazy val webServer = new WebServer(jettyConfiguration)

  def webResource = newAuthentifyingClient().resource(new URI(s"http://127.0.0.1:$portNumber"))

  def start() {
    webServer.start()
  }

  def close() {
    webServer.close()
  }
}