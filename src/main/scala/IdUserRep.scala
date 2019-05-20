import cats.Id

class IdUserRep extends UserRepository[Id] {
  override def registerUser(username: String): Id[User] = {
    val user = User( Storage.userStorageById.size, username)
    Storage.userStorageById = Storage.userStorageById + (user.id -> user)
    Storage.userStorageByName =Storage.userStorageByName + (user.username -> user)
    user //??
  }

  override def getById(id: Long): Id[Option[User]] = Storage.userStorageById.get(id)

  override def getByUsername(username: String): Id[Option[User]] = Storage.userStorageByName.get(username)
}