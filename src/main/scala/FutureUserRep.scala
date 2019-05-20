import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FutureUserRep extends UserRepository[Future] {
  override def registerUser(username: String): Future[User] = Future {
    val user = User( Storage.userStorageById.size, username)
    Storage.userStorageById = Storage.userStorageById + (user.id -> user)
    Storage.userStorageByName = Storage.userStorageByName + (user.username -> user)
    Thread.sleep(1000)
    user
  }

  override def getById(id: Long): Future[Option[User]] = Future {
    Thread.sleep(1000)
    Storage.userStorageById.get(id)
  }

  override def getByUsername(username: String): Future[Option[User]] = Future {
    Thread.sleep(1000)
    Storage.userStorageByName.get(username)
  }

}
