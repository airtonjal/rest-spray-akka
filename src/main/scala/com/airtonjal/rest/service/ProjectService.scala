package com.airtonjal.rest.service

import akka.actor._
import akka.event.Logging
import com.gettyimages.spray.swagger.SwaggerHttpService
import com.airtonjal.rest.data.ProjectRepository
import com.airtonjal.rest.model.Project
import com.wordnik.swagger.annotations._
import com.wordnik.swagger.model.ApiInfo
import grizzled.slf4j.Logging
import org.json4s.{DefaultFormats, Formats}
import spray.http.StatusCodes._
import spray.http.MediaTypes
import spray.httpx.Json4sSupport
import spray.httpx.unmarshalling._
import spray.routing._

import scala.language.postfixOps
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

case class NewProjectRequest(uid: Int, name: String, description: String, meta: Option[String] = None)

/**
 * REST Service actor
 * @author <a href="mailto:airtonjal@gmail.com">Airton Libório</a>
 */
@Api(value = "/project", basePath = "/project", description = "User projects operations", produces = "application/json")
trait ProjectService extends HttpServiceActor with Logging {
  val projectDS: ProjectRepository

  val PREFIX = "project"

  import Json4sProtocol._

//  @ApiOperation(value = "List projects", notes = "", nickname = "listProject", httpMethod = "GET")
//  @ApiImplicitParams(Array())
//  @ApiResponses(Array(
//    new ApiResponse(code = 200, message = "Projects listed")
//  ))

  @ApiOperation(value = "Get all projects", nickname = "listProject", httpMethod = "GET", produces = "application/json")
  val listRoute = pathPrefix(PREFIX) {
    (path("list") & get) {
      logRequestResponse("3", Logging.InfoLevel) {
        respondWithMediaType(MediaTypes.`application/json`) {
          onComplete(projectDS.fetchAll()) {
            case Success(f) =>
              complete(f)
            case Failure(ex) =>
              error("Some error occurred while fetching all projects from the persistent unit", ex)
              complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
          }
        }
      }
    }
  }

//  @ApiOperation(value = "Add project", notes = "", nickname = "newProject", httpMethod = "PUT")
//  @ApiParam(name = "pid", value = "The project id", required = true)
//  @ApiResponses(Array(
//    new ApiResponse(code = 501, message = "Unsupported HTTP method")
//  ))
  val addRoute = pathPrefix(PREFIX) {
    (path("new") & put) {
      entity(as[NewProjectRequest]) { np =>
        logRequestResponse("3", Logging.InfoLevel) {
          respondWithMediaType(MediaTypes.`application/json`) {
            complete(projectDS.insert(Project(None, np.uid, np.name, np.description, np.meta)))
          }
        }
      }
    }
  }

//  @ApiOperation(value = "Delete project", notes = "", nickname = "deleteProject", httpMethod = "DELETE")
//  @ApiImplicitParams(Array(
//    new ApiImplicitParam(name = "pid", value = "The project id", required = true, dataType = "Integer", paramType = "body")
//  ))
  val deleteRoute = pathPrefix(PREFIX) {
    (path("delete" / IntNumber) & delete) { pid =>
      logRequestResponse("3", Logging.InfoLevel) {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete(projectDS.delete(pid))
        }
      }
    }
  }

  val routes = listRoute ~ addRoute ~ deleteRoute

}

/* Used to mix in Spray's Marshalling Support with json4s */
object Json4sProtocol extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats
}

trait ProjectServiceImpl extends ProjectService with ActorLogging {

  import org.apache.commons.lang3.exception.ExceptionUtils

  implicit val myRejectionHandler = RejectionHandler {
    case MalformedRequestContentRejection(msg, throwable) :: _ =>
      throwable match {
        case Some(t) => log.error(ExceptionUtils.getStackTrace(t))
        case None =>
      }
      complete(BadRequest, "Malformed json received")
  }

  override def receive: Receive = runRoute(routes ~ swaggerService.routes ~
    get {
      pathPrefix("swagger") {
        pathEndOrSingleSlash {
          getFromResource("swagger/index.html")
        }
      } ~ getFromResourceDirectory("swagger")
    })


  implicit def executionContext = actorRefFactory.dispatcher

  val swaggerService = new SwaggerHttpService {
    override def apiTypes = Seq(typeOf[ProjectService])
    override def apiVersion = "2.0"
    override def baseUrl = "localhost:8777" // let swagger-ui determine the host and port
    override def docsPath = "api-docs"
    override def actorRefFactory = context

    override def apiInfo = Some(
      new ApiInfo("Maps backend service", "Backend component for Hotspots project", "TOC Url",
        "Airton Libório @airtonjal","Apache V2", "http://www.apache.org/licenses/LICENSE-2.0"))
  }

}