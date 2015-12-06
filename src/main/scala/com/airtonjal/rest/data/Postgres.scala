package com.airtonjal.rest.data

import com.airtonjal.rest.Config._
import com.airtonjal.rest.data.persistence.Projects
import grizzled.slf4j.Logging
import _root_.slick.driver.PostgresDriver.api._
import _root_.slick.jdbc.meta.MTable

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.language.postfixOps

object Postgres extends Logging {
  logger.info("port: " + dbPort + "\thost: " + dbHost)

  val db = Database.forConfig("pg")

  implicit val session: Session = db.createSession()

  val PROJECTS_TABLE = "PROJECT"

  def startPostgres() = {
    val tables = Await.result(db.run(MTable.getTables), 10.seconds).toList
    if (!tables.exists(t => t.name.name.toUpperCase == PROJECTS_TABLE)) {
      logger.info(s"$PROJECTS_TABLE table not found creating it")
      Await.result(db.run(Projects.schema.create), 2 seconds)
    } else logger.info(s"$PROJECTS_TABLE table is already created")
  }

}