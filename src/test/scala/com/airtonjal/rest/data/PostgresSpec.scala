package com.airtonjal.rest.data

import com.airtonjal.rest.Config
import com.airtonjal.rest.data.persistence.Projects
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, WordSpec}
import slick.driver.PostgresDriver.api._
import slick.jdbc.meta.MTable

import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * Tests set up
 * @author <a href="mailto:airton.liborio@webradar.com">Airton Libório</a>
 */
class PostgresSpec extends WordSpec with BeforeAndAfterAll with ScalaFutures {

  val db = Postgres.db
  val schema = Projects.schema
  val projects = Projects.ProjectTable
  val databaseName = Config.dbDatabase

//  Await.result(db.run(sqlu"""CREATE SCHEMA $databaseName"""), 10 seconds)

  override def beforeAll() {
    Postgres.startPostgres()
  }

  // Uncomment and f*ck your tests >(
//  override def afterAll() {
//    db.close()
//  }

  "PostgreSQL database" should {

    "contain projects table " in {
      val getTables = db.run(MTable.getTables)

      whenReady(getTables) { tables =>
        assert(tables.exists(t => t.name.name.toUpperCase == projects.baseTableRow.tableName.toUpperCase))
      }
    }

    s"empty ${projects.baseTableRow.tableName} table" in {
      val f = db.run(projects.delete)

      assert(f.isReadyWithin(10 second))
    }
  }


}
