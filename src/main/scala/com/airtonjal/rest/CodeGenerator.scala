package com.airtonjal.rest

import com.typesafe.config.ConfigFactory

/**
 * Generates stubs for PostgreSQL tables
 * @author <a href="mailto:airtonjal@gmail.com">Airton Libório</a>
 */
object CodeGenerator {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val port = config.getInt("pg.port")
    val host = config.getString("pg.host")
    val database = config.getString("pg.database")
    val driver = config.getString("pg.driver")

    generateCode(port, host, "airton")
    System.exit(0)
  }

  def generateCode(port: Int, host: String, schema: String) = {
    slick.codegen.SourceCodeGenerator.run(
      "slick.driver.PostgresDriver",
      "org.postgresql.Driver",
      s"jdbc:postgresql://$host:$port/$schema",
      "/Users/airton/dev/hotspots/maps-backend/generated",
      "com.webradar.maps.dao.data", None, None
    )
  }

}
