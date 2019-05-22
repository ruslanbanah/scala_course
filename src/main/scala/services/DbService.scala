package services

import config.DatabaseConfig
import org.flywaydb.core.Flyway

import scala.util.Try

/**
  * Database migration
  */
class DbService(dbConfig: DatabaseConfig) {

  private[this] val flyway = new Flyway()
  flyway.setDataSource(dbConfig.jdbcUrl, dbConfig.user, dbConfig.password)

  def migrateDatabaseSchema(): Int = Try(flyway.migrate()).getOrElse {
    flyway.repair()
    flyway.migrate()
  }

  def dropDatabase(): Unit = flyway.clean()
}
