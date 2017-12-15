package models

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class User (id: Long, email: String, firstName: String, lastName: String, gender: String, birthDate: Timestamp)

class UserTable(tag: Tag) extends Table[User](tag, "users") {
  val id = column[Long]("id", O.PrimaryKey)
  val email = column[String]("email")
  val firstName = column[String]("firstName")
  val lastName = column[String]("lastName")
  val gender = column[String]("gender")
  val birthDate = column[Timestamp]("birthDate")

  def * = (id, email, firstName, lastName, gender, birthDate) <> (User.apply _ tupled, User.unapply)
}

object UserTable {
  val table = TableQuery[UserTable]
}

class UserRepository(db: Database) {
  val userTableQuery = TableQuery[UserTable]

  def selectUsers() =
    db.run(userTableQuery.result)

  def create(user: User): Future[User] =
    db.run(userTableQuery returning userTableQuery += user)

  def update(user: User): Future[Int] =
    db.run(userTableQuery.filter(_.id === user.id).update(user))

  def delete(userId: Long): Future[Int] =
    db.run(userTableQuery.filter(_.id === userId).delete)

  def getById(userId: Long): Future[Option[User]] =
    db.run(userTableQuery.filter(_.id === userId).result.headOption)
}



