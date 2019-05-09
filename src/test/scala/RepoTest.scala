import org.scalatest._
import Matchers._

class RepoTest extends FlatSpec {
  val idUserRepository = new IdUserRep()
  val idDeviceRepository =  new IdDeviceRep()

  val futureUserRepository = new FutureUserRep()
  val futureDeviceRepository =  new FutureDeviceRep()

  val users = List("Bob", "Jon", "Ava")
  val devices = List("device1", "device2", "device3")

  val userService = new UserService(idUserRepository)
  val deviceService = new IotDeviceService(idDeviceRepository, idUserRepository)

  it should s"Created Users $users" in {
    users.map(user => userService.registerUser(user))
    users.map(userName => userService.getByUsername(userName).fold("")(user => user.username) shouldEqual userName)
    users.zipWithIndex.foreach{ case (userName, i) => userService.getById(i).fold("")(user => user.username) shouldEqual userName }
  }

  it should s"Created Users with exist userName" in {
    users.map(user => userService.registerUser(user) should be ('left))
  }

  it should s"Created Devices $devices" in {
    users.zipWithIndex.foreach{ case (_, userId) => {
      devices.map(deviceSn => deviceService.registerDevice(userId, s"$userId-$deviceSn") should be ('right))
    }
    }
  }

  it should s"Created Devices with exist serial number" in {
    users.zipWithIndex.foreach{ case (_, userId) => {
      devices.map(deviceSn => deviceService.registerDevice(userId, s"$userId-$deviceSn") should be ('left))
    }
    }
  }

}