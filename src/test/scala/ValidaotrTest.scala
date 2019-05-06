
import org.scalatest._
import Matchers._
import Validator._

class ValidatorTest extends FlatSpec {
  val bob = Person(name = "Bob", age = 30)
  val alex = Person(name = "Alex", age = 200)
  val a = 5
  val b = -5

  it should "return positive result" in {
    a.validate shouldEqual Right(a)
  }
  it should "return exception message" in {
    (b).validate shouldEqual Left(s"$b should be greater then 0")
  }
  it should s"if $b less then $a return $b" in {
    b validate lessThan(a) shouldEqual Right(b)
  }
  it should s"if $a not less then $b return a warning message" in {
    a validate lessThan(b) shouldEqual Left(s"$a should be less than $b")
  }

  it should "return a warning message" in {
    "".validate shouldEqual Left("Value cannot be empty")
  }
  it should "return valid string" in {
    "zxy".validate shouldEqual Right("zxy")
  }

  it should "return valid value" in {
    bob.validate shouldEqual Right(bob)
  }
  it should "return msg about invalid value" in {
    alex.validate shouldEqual Left("Invalid values")
  }
}
