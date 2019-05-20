import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FutureDeviceRep extends IotDeviceRepository[Future] {
  override def registerDevice(userId: Long, serialNumber: String): Future[IotDevice] = Future {
    val device = IotDevice( Storage.deviceStorageById.size, userId , serialNumber)
    Storage.deviceStorageById = Storage.deviceStorageById + (device.id -> device)
    Storage.deviceStorageBySn = Storage.deviceStorageBySn + (device.sn -> device)
    Storage.deviceStorageByUserId = Storage.deviceStorageByUserId.updated(userId, Storage.deviceStorageByUserId.getOrElse(userId, Seq()) :+ device)
    Thread.sleep(1000)
    device
  }

  override def getById(id: Long): Future[Option[IotDevice]] = Future {
    Thread.sleep(1000)
    Storage.deviceStorageById.get(id)
  }

  override def getBySn(sn: String): Future[Option[IotDevice]] = Future {
    Thread.sleep(1000)
    Storage.deviceStorageBySn.get(sn)
  }

  override def getByUser(userId: Long): Future[Seq[IotDevice]] = Future {
    Thread.sleep(1000)
    Storage.deviceStorageByUserId.getOrElse(userId, Seq())
  }
}