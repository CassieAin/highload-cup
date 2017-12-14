import slick.jdbc.PostgresProfile.api._

package object models {
  implicit val dateTimeToTimeStampMapper =
    MappedColumnType.base[java.time.LocalDateTime, java.sql.Timestamp](java.sql.Timestamp.valueOf, _.toLocalDateTime)
}
