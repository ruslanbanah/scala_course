import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpRequest, MessageEntity}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import services.{DbUserRepository, UserService}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import routes.UserRoute
import cats.implicits._
import models.User
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val td = jsonFormat4(User.apply)
}

class UserApiTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest  {
  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("db.h2")
  val userService = new UserService(new DbUserRepository(dbConfig))
  val route = new UserRoute(userService).routes

  "User POST" in {
    "be able to create user(POST: /user)" in {
      val user = User(1, "Zenyk", Some("Lviv"), "zenyk@email.com")
      val userMarshal = Marshal(user).to[MessageEntity].futureValue

      val request = Post("/user").withEntity(userMarshal)

      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===("""{"address":"Lviv","email":"zenyk@email.com","id":2,"username":"Zenyk"}""")
      }
    }
  }
  "User GET" should {
    "Get existing user by id (GET: /user?id=1)" in {
      val request = HttpRequest(uri = "/user?id=1")
      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===("""{"address":"Kanalveien 52c","email":"mio@mail.com","id":1,"username":"User#1"}""")
      }
    }
    "Get existing user by id (GET: /user?id=2)" in {
      val request = HttpRequest(uri = "/user?id=2")
      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===("""{"address":"Lviv","email":"zenyk@email.com","id":2,"username":"Zenyk"}""")
      }
    }
    "Get no user (GET: /user?id=0)" in {
      val request = HttpRequest(uri = "/user?id=0")
      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===("")
      }
    }
  }
}
