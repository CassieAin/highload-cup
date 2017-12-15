package models

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

case class Location (id: Long, place: String, country: String, city: String, distance: Long)

class LocationTable(tag: Tag) extends Table[Location](tag, "locations") {
  val id = column[Long]("id", O.PrimaryKey)
  val place = column[String]("place")
  val country = column[String]("country")
  val city = column[String]("city")
  val distance = column[Long]("distance")

  def * = (id, place, country, city, distance) <> (Location.apply _ tupled, Location.unapply)
}

object LocationTable {
  val table = TableQuery[LocationTable]
}

class LocationRepository(db: Database) {
  val locationTableQuery = TableQuery[LocationTable]

  def selectLocations() =
    db.run(locationTableQuery.result)

  def create(location: Location): Future[Location] =
    db.run(locationTableQuery returning locationTableQuery += location)

  def update(location: Location): Future[Int] =
    db.run(locationTableQuery.filter(_.id === location.id).update(location))

//  def updateById(locationId: Long): Future[Int] =
//    db.run(locationTableQuery.filter(_.id === locationId).update())

  def delete(locationId: Long): Future[Int] =
    db.run(locationTableQuery.filter(_.id === locationId).delete)

  def getById(locationId: Long): Future[Option[Location]] =
    db.run(locationTableQuery.filter(_.id === locationId).result.headOption)
}
