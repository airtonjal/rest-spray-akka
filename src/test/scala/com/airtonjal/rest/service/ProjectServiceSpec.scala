package com.airtonjal.rest.service

import akka.testkit.{TestActorRef, TestProbe}
import com.airtonjal.rest.data.ProjectRepositoryImpl
import com.airtonjal.rest.model.Project
import org.scalatest.{Matchers, WordSpec}
import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest

/**
 * [[com.airtonjal.rest.service.ProjectService]] Spec
 * @author <a href="mailto:airton.liborio@webradar.com">Airton Libório</a>
 */
class ProjectServiceSpec extends WordSpec with ScalatestRouteTest with Matchers {

  val projectsService = TestProbe()

  def restService = TestActorRef(
    new ProjectServiceImpl { val projectDS = new ProjectRepositoryImpl })

  import Json4sProtocol._

  val routes = restService.underlyingActor.routes

  "Project service" should {
    "list all projects" in {
      Get("/project/list") ~> routes ~> check {
        status === OK
        val a = responseAs[Seq[Project]]
      }
    }

    val project = NewProjectRequest(1, "project test", "my project test in scalatest", Some("metadata"))
    "add new project" in {
      Put("/project/new", project) ~> routes ~> check {
        status === OK
      }
    }

    val projectWithoutMeta = NewProjectRequest(1, "project test", "my project test in scalatest without metadata")
    "add new project without metadata" in {
      Put("/project/new", projectWithoutMeta) ~> routes ~> check {
        status === OK
      }
    }

    var projects: Seq[Project] = Seq.empty[Project]
    "return 2 projects" in {
      Get("/project/list") ~> routes ~> check {
        status === OK
        responseAs[Seq[Project]] should have size 2
        projects ++= responseAs[Seq[Project]]
      }
    }

    "delete created projects" in {
      projects.foreach{ p =>
        p.pid match {
          case Some(pid: Int) =>
            Delete(s"/project/delete/$pid") ~> routes ~> check {
              status === OK
            }
          case None => fail("Project was not properly created, no value for pid property")
        }
      }
    }
  }

}