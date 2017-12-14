package application
import models.{LocationRepository, UserRepository, VisitRepository}
import slick.jdbc.PostgresProfile.api._

trait MyDatabases {
  val db = Database.forConfig("scalaxdb")
  val locationRepository = new LocationRepository(db)
  val userRepository = new UserRepository(db)
  val visitRepository = new VisitRepository(db)
}
