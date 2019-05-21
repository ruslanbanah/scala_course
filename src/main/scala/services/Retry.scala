package services

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.ExecutionContext.Implicits.global

trait Retry {
  def retryFuture[A](block: () => Future[A],
                     acceptResult: A => Boolean,
                     retries: List[FiniteDuration]): Future[A] = {
    block().flatMap { a =>
      if (acceptResult(a)) {
        Future.successful(a)
      } else {
        retries match {
          case Nil => Future.successful(a)
          case x :: xs => {
            Thread.sleep(x.toMillis)
            retryFuture(block, acceptResult, xs)
          }
        }
      }
    }
  }
}
