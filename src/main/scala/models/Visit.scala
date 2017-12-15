package models

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class Visit (id: Long, location: Long, user: Long, visitedAt: Timestamp, mark: Int)

class VisitTable(tag: Tag) extends Table[Visit](tag, "visits") {
  val id = column[Long]("id", O.PrimaryKey)
  val location = column[Long]("location")
  val user = column[Long]("user")
  val visitedAt = column[Timestamp]("visitedAt")
  val mark = column[Int]("mark")

  val locationFk = foreignKey("location_id_fk", location, TableQuery[LocationTable])(_.id)
  val userFk = foreignKey("user_id_fk", user, TableQuery[UserTable])(_.id)
  def * = (id, location, user, visitedAt, mark) <> (Visit.apply _ tupled, Visit.unapply)
}

object VisitTable {
  val table = TableQuery[VisitTable]
}

class VisitRepository(db: Database) {
  val visitTableQuery = TableQuery[VisitTable]

  def selectVisits() =
    db.run(visitTableQuery.result)

  def create(visit: Visit): Future[Visit] =
    db.run(visitTableQuery returning visitTableQuery += visit)

  def update(visit: Visit): Future[Int] =
    db.run(visitTableQuery.filter(_.id === visit.id).update(visit))

  def delete(visitId: Long): Future[Int] =
    db.run(visitTableQuery.filter(_.id === visitId).delete)

  def getById(visitId: Long): Future[Option[Visit]] =
    db.run(visitTableQuery.filter(_.id === visitId).result.headOption)

  def getAverageMark(): Future[Option[Int]] =
    db.run(visitTableQuery.map(_.mark).avg.result)

  def getVisitsByUser(userId: Long) =
    db.run(visitTableQuery.filter(_.user === userId).result)

  def getVisitsMarks(locationId: Long) =
    db.run(visitTableQuery.filter(_.location === locationId).map(_.mark).avg.result)
}
