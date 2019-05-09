import scala.concurrent.Future

class FutureDeviceRep extends IotDeviceRepository[Future] {
  override def registerDevice(userId: Long, serialNumber: String): Future[IotDevice] = {
    val device = IotDevice( Storage.deviceStorageById.size, userId , serialNumber)
    Storage.deviceStorageById = Storage.deviceStorageById + (device.id -> device)
    Storage.deviceStorageBySn = Storage.deviceStorageBySn + (device.sn -> device)
    Storage.deviceStorageByUserId = Storage.deviceStorageByUserId.updated(userId, Storage.deviceStorageByUserId.getOrElse(userId, Seq()) :+ device)
    Thread.sleep(1000)
    Future.successful{device}
  }

  override def getById(id: Long): Future[Option[IotDevice]] = {
    Thread.sleep(1000)
    Future.successful{Storage.deviceStorageById.get(id)}
  }

  override def getBySn(sn: String): Future[Option[IotDevice]] = {
    Thread.sleep(1000)
    Future.successful{Storage.deviceStorageBySn.get(sn)}
  }

  override def getByUser(userId: Long): Future[Seq[IotDevice]] = {
    Thread.sleep(1000)
    Future.successful{ Storage.deviceStorageByUserId.getOrElse(userId, Seq()) }
  }
}