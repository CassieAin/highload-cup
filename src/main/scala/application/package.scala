import java.sql.Timestamp

import io.circe.Decoder.Result
import io.circe._
import models.{Location, User, Visit}


package object application {

  implicit val locationEncoder = new Encoder[Location] {
    final def apply(location: Location): Json = Json.obj(
      ("id", Json.fromLong(location.id)),
      ("place", Json.fromString(location.place)),
      ("country", Json.fromString(location.country)),
      ("city", Json.fromString(location.city)),
      ("distance", Json.fromLong(location.distance))
    )
  }

  implicit val visitEncoder = new Encoder[Visit] {
    final def apply(visit: Visit): Json = Json.obj(
      ("id", Json.fromLong(visit.id)),
      ("location", Json.fromLong(visit.location)),
      ("user", Json.fromLong(visit.user)),
      ("visitedAt", TimestampFormat.apply(visit.visitedAt)),
      ("mark", Json.fromLong(visit.mark))
    )
  }

  implicit val userEncoder = new Encoder[User] {
    final def apply(user: User): Json = Json.obj(
      ("id", Json.fromLong(user.id)),
      ("email", Json.fromString(user.email)),
      ("firstName", Json.fromString(user.firstName)),
      ("lastName", Json.fromString(user.lastName)),
      ("gender", Json.fromString(user.gender)),
      ("birthDate", TimestampFormat.apply(user.birthDate))
    )
  }

  implicit val TimestampFormat : Encoder[Timestamp] with Decoder[Timestamp] = new Encoder[Timestamp] with Decoder[Timestamp] {
    override def apply(a: Timestamp): Json = Encoder.encodeLong.apply(a.getTime)

    override def apply(c: HCursor): Result[Timestamp] = Decoder.decodeLong.map(s => new Timestamp(s)).apply(c)
  }
}
