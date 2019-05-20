import java.util.concurrent.atomic.AtomicLong
import cats.Id

class IdDeviceRep extends IotDeviceRepository[Id] {
  override def registerDevice(userId: Long, serialNumber: String): Id[IotDevice] = {
    val idGen = new AtomicLong(0L)
    val device = IotDevice( idGen.incrementAndGet(), userId , serialNumber)
    Storage.deviceStorageById = Storage.deviceStorageById + (device.id -> device)
    Storage.deviceStorageBySn = Storage.deviceStorageBySn + (device.sn -> device)
    Storage.deviceStorageByUserId = Storage.deviceStorageByUserId.updated(userId, Storage.deviceStorageByUserId.getOrElse(userId, Seq()) :+ device)
    device
  }

  override def getById(id: Long): Id[Option[IotDevice]] = Storage.deviceStorageById.get(id)

  override def getBySn(sn: String): Id[Option[IotDevice]] = Storage.deviceStorageBySn.get(sn)

  override def getByUser(userId: Long): Id[Seq[IotDevice]] = Storage.deviceStorageByUserId.getOrElse(userId, Seq())
}