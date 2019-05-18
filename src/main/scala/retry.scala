import scala.concurrent.duration._
import scala.annotation.tailrec

object start extends App {
  @tailrec
  final def retry[A](block: () => A,
                     acceptResult: A => Boolean,
                     retries: List[FiniteDuration]): A = {
    val res = block()
    if (retries.isEmpty || acceptResult(res))
      res
    else {
      Thread.sleep(retries.head.toMillis)
      retry(block, acceptResult, retries.tail)
    }
  }
  def counter(x: Int) =  {
    var count = x
    () => {
      val resVal = count
      count = count + 1
      resVal
    }
  }

  val inc = counter(1)
  val block = () => {
    val a = inc()
    println(s"Run block ${a}")
    a
  }
  val result = retry[Int](
    block,
    acceptResult = res => res == 5,
    retries = List(0.seconds, 2.seconds, 5.seconds, 6.seconds)
  )
  println(s"Result: ${result}")
}
