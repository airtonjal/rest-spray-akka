package com.airtonjal.rest

import akka.actor.{ActorSystem, Props, _}
import akka.io.IO
import com.airtonjal.rest.Config._
import com.airtonjal.rest.data.{ProjectRepositoryImpl, Postgres}
import com.airtonjal.rest.service.ProjectServiceImpl
import grizzled.slf4j.Logging
import spray.can.Http

import scala.language.postfixOps

/**
 * Application entry point
 * @author <a href="mailto:airtonjal@gmail.com">Airton Libório</a>
 */
object Main extends Logging {

  slick.driver.PostgresDriver

  def main (args: Array[String]) {
    info("Starting application")

    implicit val system = ActorSystem("maps-backend-system")

    // Our service implementation
    val graphService = system.actorOf(
      Props(new ProjectServiceImpl { val projectDS = new ProjectRepositoryImpl }), "project-rest-service")

    Postgres.startPostgres()

    // Starts http server, hardcoded properties only for the sake of demonstration
    IO(Http) ! Http.Bind(graphService, interface = serverHost, port = serverPort)
  }

}