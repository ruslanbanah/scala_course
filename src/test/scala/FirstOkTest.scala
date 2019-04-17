import org.scalatest.FunSuite
import start._
import scala.concurrent.duration._

class FirstOkTest extends FunSuite {
  test("First ok : ") {
    val block = counter(1)
    val res = retry[Int](
      block,
      acceptResult = res => res % 2 == 0,
      retries = List(0.seconds, 0.seconds, 0.seconds, 0.seconds)
    )
    assert(res == 2)
  }
}
