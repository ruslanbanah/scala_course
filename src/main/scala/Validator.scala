/**
  * Implement validator typeclass that should validate arbitrary value [T].
  * @tparam T the type of the value to be validated.
  */
trait Validator[T] { self =>
  /**
    * Validates the value.
    * @param value value to be validated.
    * @return Right(value) in case the value is valid, Left(message) on invalid value
    */
  def validate(value: T): Either[String, T]

  /**
    * And combinator.
    * @param other validator to be combined with 'and' with this validator.
    * @return the Right(value) only in case this validator and <code>other</code> validator returns valid value,
    *         otherwise Left with error messages from both validators.
    */
  def and(other: Validator[T]): Validator[T] = (value: T) => {
    self.validate(value).flatMap(_ => other.validate(value))
  }

  /**
    * Or combinator.
    * @param other validator to be combined with 'or' with this validator.
    * @return the Right(value) only in case either this validator or <code>other</code> validator returns valid value,
    *         otherwise Left with error messages from the failed validator or from both if both failed.
    */
  def or(other: Validator[T]): Validator[T] = (value: T) => {
    self.validate(value) match {
      case Left(err) => other.validate(value) match {
        case Right(_) => Right(value)
        case Left(e2) => Left(s"$err\n$e2")
      }
      case Right(_) => Right(value)
    }

    /**
      * Different way
      * self.validate(value).left.flatMap(e1 => other.validate(value).left.map(e2 => s"$e1\n$e2"))
      *
      */

  }
}


object Validator {
  val positiveInt : Validator[Int] = new Validator[Int] {
    // implement me
    override def validate(t: Int): Either[String, Int] = {
      if (t > 0) Right(t) else Left(s"$t should be greater then 0")
    }
  }

  def lessThan(n: Int): Validator[Int] = new Validator[Int] {
    // implement me
    override def validate(t: Int): Either[String, Int] = {
      if (t < n) Right(t) else Left(s"$t should be less than $n")
    }
  }

  val nonEmpty : Validator[String] = new Validator[String] {
    // implement me
    override def validate(t: String): Either[String, String] = {
      if (t.trim.nonEmpty) Right(t) else Left(s"Value cannot be empty")
    }
  }

  val isPersonValid = new Validator[Person] {
    // implement me
    // Returns valid only when the name is not empty and age is in range [1-99].
    override def validate(value: Person): Either[String, Person] = {
      if (!value.name.isEmpty && (value.age >0 && value.age<100)) Right(value) else Left("Invalid values")
    }

  }
  implicit class ValidateInt(n: Int) {
    def validate(implicit v: Validator[Int] = Validator.positiveInt): Either[String, Int] = v.validate(n)
  }

  implicit class ValidateString(s: String) {
    def validate(implicit v: Validator[String] = Validator.nonEmpty): Either[String, String] = v.validate(s)
  }

  implicit class ValidatePerson(p: Person) {
    def validate(implicit v: Validator[Person] = Validator.isPersonValid): Either[String, Person] = v.validate(p)
  }
}

object ValidApp {
  import Validator._

  // uncomment make possible next code to compile
  2 validate (positiveInt or lessThan(10))

  // uncomment make possible next code to compile
  "" validate Validator.nonEmpty

  // uncomment make possible next code to compile
  Person(name = "John", age = 25) validate isPersonValid
}

object ImplicitValidApp {
  import Validator._

  // uncomment next code and make it compilable and workable
  Person(name = "John", age = 25) validate;
  "asdasd" validate;
  234.validate
}


case class Person(name: String, age: Int)