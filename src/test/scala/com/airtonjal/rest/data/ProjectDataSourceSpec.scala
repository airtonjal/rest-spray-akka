package com.airtonjal.rest.data

import com.airtonjal.rest.model.Project
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{MustMatchers, WordSpec}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Failure

/**
 * Projects data spec
 * @author <a href="mailto:airton.liborio@webradar.com">Airton Libório</a>
 */
class ProjectDataSourceSpec extends WordSpec with MustMatchers with ScalaFutures {

  val projectDS = new ProjectRepositoryImpl

  "Project Data Source" should {
    "delete projects" in {
      val f = projectDS.deleteAll

      Await.ready(f, 10 second).value.get match {
        case Failure(e) =>
          e.printStackTrace()
          fail("Could not delete all projects")
        case _ =>
      }
    }

    "insert projects" in {
      val f = projectDS.insert(Seq(
        Project(uid = 1, name = "my project", description = "my project attempt"),
        Project(uid = 1, name = "another project", description = "another my project attempt"),
        Project(uid = 1, name = "another one", description = "one more project attempt")
      ))

      whenReady(f) { result =>
        result must be (Some(3))
      }
    }

    "contain 3 projects" in {
      val f = projectDS.fetchAll

      whenReady(f) { result =>
        result must have length 3
      }
    }

    "clean test records" in {
      val f = projectDS.deleteAll

      Await.ready(f, 10 second).value.get match {
        case Failure(e) => fail("Could not delete all projects")
        case _ =>
      }
    }
  }


}
