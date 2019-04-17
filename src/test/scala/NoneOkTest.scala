import org.scalatest.FunSuite
import start._
import scala.concurrent.duration._

class AllRejectTest extends FunSuite {
  test("All reject : ") {
    val block = counter(400)
    val res = retry[Int](
      block,
      acceptResult = res => res == 4,
      retries = List(0.seconds, 0.seconds, 0.seconds, 0.seconds)
    )
    assert(res == 404)
  }
}