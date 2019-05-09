import org.scalatest._
import Matchers.{be, _}
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.higherKinds
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


class RepoTest extends FlatSpec {
  val idUserRepository = new IdUserRep()
  val idDeviceRepository =  new IdDeviceRep()

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

  def await[T](f: Future[T]) = Await.result(f, 2.second)

  val futureUserRepository = new FutureUserRep
  val futureDeviceRepository =  new FutureDeviceRep
  val futureUserService = new UserService(futureUserRepository)
  val futureDeviceService = new IotDeviceService(futureDeviceRepository, futureUserRepository)


  it should s"Created future Users $users" in {
    users.map(user => futureUserService.registerUser(user))
    users.map(userName => await(futureUserService.getByUsername(userName)).fold("")(user => user.username) shouldEqual userName)
    users.zipWithIndex.foreach{ case (userName, i) => await(futureUserService.getById(i)).fold("")(user => user.username) shouldEqual userName }
  }

  it should s"Created future Devices $devices" in {
    users.zipWithIndex.foreach{ case (_, userId) => {
      devices.map(deviceSn => await(futureDeviceService.registerDevice(userId, s"future$userId-$deviceSn")) should be ('right))
    }
    }
  }

  it should s"Created future Devices with exist serial number" in {
    users.zipWithIndex.foreach{ case (_, userId) => {
      devices.map(deviceSn => await(futureDeviceService.registerDevice(userId, s"$userId-$deviceSn")) should be ('left))
    }
    }
  }


}