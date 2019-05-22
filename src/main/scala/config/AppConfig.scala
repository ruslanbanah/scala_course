package config
import com.typesafe.config.ConfigFactory

trait HttpConfig {
  def root: String
  def port: Int
}

trait DatabaseConfig {
  def port: Int
  def databaseName: String
  def jdbcUrl: String
  def user: String
  def password: String
}

class AppConfig {
  private val config =  ConfigFactory.load()

  val applicationName: String = config.getString("app.name")

  val httpConfig: HttpConfig = new HttpConfig {
    val root: String = config.getString("http.root")
    val port: Int = config.getInt("http.port")
  }

  val databaseConfig: DatabaseConfig = new DatabaseConfig {
    private val databaseConfig = config.getConfig(s"db.postgre.db")

    lazy val port: Int = databaseConfig.getInt("port")
    lazy val databaseName: String = databaseConfig.getString("databaseName")
    lazy val jdbcUrl: String = databaseConfig.getString("url")
    lazy val user: String = databaseConfig.getString("user")
    lazy val password: String = databaseConfig.getString("password")
  }
}