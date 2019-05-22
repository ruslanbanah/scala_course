package routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}

import scala.concurrent.{ExecutionContext, Future}
import com.typesafe.scalalogging.Logger
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import model.JsonSupport
import services.Validator._
import services._
import models._

class UserRoute(userService: UserService[Future])(implicit ex: ExecutionContext) extends JsonSupport {
  val logger = Logger(this.getClass)

  implicit def exceptionHandler = ExceptionHandler {
    case e: Exception =>
      extractUri { uri =>
        logger.error("Exception: ", e)
        complete(HttpResponse(InternalServerError, entity = e.getMessage))
      }
  }
  val routes: Route = Route.seal(pathPrefix("user") {
    get {
      parameter('id.as[Long]) { id =>
        complete {
          userService.getById(id)
        }
      }
    } ~ post {
      entity(as[User]) { u =>
        complete {
          u.username validate (nonEmpty and strLessThan(3)) match {
            case Left(value) => value
            case Right(_) => userService.registerUser(u.username, u.address, u.email)
          }

        }
      }
    }
  })
}