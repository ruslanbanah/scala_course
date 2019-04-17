import scala.annotation.tailrec
import scala.concurrent.duration._

object start extends App {
  @tailrec
  final def retry[A](block: () => A,
                     acceptResult: A => Boolean,
                     retries: List[FiniteDuration]): A = {
  }
  retry[Int](
    block = () => 1 + 1,
    acceptResult = res => res % 2 == 0,
    retries = List(0.seconds, 1.seconds, 2.seconds, 3.seconds)
  )
}