object Storage {
  var userStorageById: Map[Long, User] = Map()
  var userStorageByName: Map[String, User] = Map()

  var deviceStorageById: Map[Long, IotDevice] = Map()
  var deviceStorageBySn: Map[String, IotDevice] = Map()
  var deviceStorageByUserId: Map[Long, Seq[IotDevice]] = Map()
}