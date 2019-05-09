import scala.concurrent.Future

class FutureUserRep extends UserRepository[Future] {
  override def registerUser(username: String): Future[User] = {
    val user = User( Storage.userStorageById.size, username)
    Storage.userStorageById = Storage.userStorageById + (user.id -> user)
    Storage.userStorageByName =Storage.userStorageByName + (user.username -> user)
    Thread.sleep(1000)
    Future.successful{user}
  }

  override def getById(id: Long): Future[Option[User]] = {
    Thread.sleep(1000)
    Future.successful{Storage.userStorageById.get(id)}
  }

  override def getByUsername(username: String): Future[Option[User]] = {
    Thread.sleep(1000)
    Future.successful{Storage.userStorageByName.get(username)}
  }

}
