import org.scalatest.FunSuite
import start._
import scala.concurrent.duration._

class LastOkTest extends FunSuite {
  test("Last ok : ") {
    val block = counter(1)
    val res = retry[Int](
      block,
      acceptResult = res => res == 4,
      retries = List(0.seconds, 0.seconds, 0.seconds, 0.seconds)
    )
    assert(res == 4)
  }
}
