import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import config._
import services.{DbUserRepository, FlywayService, UserService}
import routes._
import cats.implicits._

import scala.io.StdIn

object Server extends App {
  val logger = Logger(this.getClass)
  val conf = new AppConfig
  val database = "postgre"

  implicit val system = ActorSystem(conf.applicationName)
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  new FlywayService(conf.databaseConfig(database)).migrateDatabaseSchema()

  val dbConfig = DatabaseConfig.forConfig[JdbcProfile](s"db.${database}")
  val userService = new UserService(new DbUserRepository(dbConfig))
  val routes = new UserRoute(userService).routes
  val bindingFuture = Http().bindAndHandle(routes, conf.httpConfig.root, conf.httpConfig.port)

  logger.info(s"Server online at http://${conf.httpConfig.root}:${conf.httpConfig.port}/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}