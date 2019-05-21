name := "scala_course"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.7" % Test
libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.22"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.0"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0"
libraryDependencies += "com.typesafe" % "config" % "1.3.4"

libraryDependencies += "org.flywaydb" % "flyway-core" % "3.2.1"
libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1211"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.8" % "test"
