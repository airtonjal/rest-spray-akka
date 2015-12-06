package com.airtonjal.rest

import java.sql.{DriverManager, Connection}

import com.airtonjal.rest.Config._
import com.airtonjal.rest.data.Postgres
import org.scalatest.{FlatSpec, ConfigMap, BeforeAndAfterAll}

import scala.language.postfixOps

/**
 * Test suite set up
 * @author <a href="mailto:airton.liborio@webradar.com">Airton Libório</a>
 */
class MapsTestSuite extends FlatSpec with BeforeAndAfterAll {

//  def db = Database.forURL(url = s"jdbc:postgresql://$dbHost:$dbPort/", driver = dbDriver)

  // Create the database for tests only
//  override def beforeAll(configMap: ConfigMap): Unit = {
//    val driver = "org.postgresql.Driver"
//    val url = s"jdbc:postgresql://$dbHost:$dbPort/"
//
//    // Using the following Slick code did not work, had to strong arm and use drive directlys
//    //Await.result(db.run(sqlu"""DROP DATABASE IF EXISTS #$dbDatabase; CREATE DATABASE #$dbDatabase"""), 10 seconds)
//
//    println(s"Database name: $dbDatabase" )
//    var connection: Connection = null
//    try {
//      // make the connection
//      Class.forName(driver)
//      connection = DriverManager.getConnection(url)
//
//      // create the statement, and run the select query
//      val statement = connection.createStatement()
//      statement.executeUpdate(s"DROP DATABASE IF EXISTS $dbDatabase; CREATE DATABASE $dbDatabase")
//      statement.close()
//
//      Postgres.startPostgres()
//    } catch {
//      case e: Exception => e.printStackTrace()
//    } finally connection.close()
//  }
//
//  "this set up" should "do nothing" in {
//    println("I'm all set up")
//  }

}
