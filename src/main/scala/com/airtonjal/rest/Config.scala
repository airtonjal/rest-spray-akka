package com.airtonjal.rest

import com.typesafe.config.ConfigFactory

/**
 * App configuration
 * @author <a href="mailto:airtonjal@gmail.com">Airton Libório</a>
 */
object Config {

  val config = ConfigFactory.load()

  def serverHost = config.getString("server.host")
  def serverPort = config.getInt("server.port")

  def dbHost     = config.getString("pg.host")
  def dbPort     = config.getInt   ("pg.port")
  def dbDatabase = config.getString("pg.database")
  def dbDriver   = config.getString("pg.driver")
  def dbURL      = config.getString("pg.url")

}
