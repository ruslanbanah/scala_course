import org.scalatest.FunSuite
import scala.concurrent.duration._
import start._

class RetryTest extends FunSuite {
  test("The first iteration passed") {
    val block = counter(1)
    val res = retry[Int](
      block,
      acceptResult = res => res % 2 == 0,
      retries = List(0.seconds, 0.seconds, 0.seconds, 0.seconds)
    )
    assert(res == 2)
  }
  test("The last iteration passed") {
    val block = counter(1)
    val res = retry[Int](
      block,
      acceptResult = res => res == 4,
      retries = List(0.seconds, 0.seconds, 0.seconds, 0.seconds)
    )
    assert(res == 4)
  }
  test("All iterations reject") {
    val block = counter(400)
    val res = retry[Int](
      block,
      acceptResult = res => res == 4,
      retries = List(0.seconds, 0.seconds, 0.seconds, 0.seconds)
    )
    assert(res == 404)
  }
  test("Empty iterations retry") {
    val block = counter(200)
    val res = retry[Int](
      block,
      acceptResult = res => res == 200,
      retries = List()
    )
    assert(res == 200)
  }
}

