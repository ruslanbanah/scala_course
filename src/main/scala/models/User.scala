package models

case class User(id: Long, username: String, address: Option[String], email: String)
