package application

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.syntax._
import models.Location

//trait Protocols extends SprayJsonSupport with DefaultJsonProtocol {
//  implicit val hotelFormat = jsonFormat4(Hotel)
//}

trait ApiRoute extends MyDatabases with FailFastCirceSupport {

  val routes =
    pathSingleSlash {
      complete("hello")
    } ~
      pathPrefix("users") {
        pathSingleSlash {
          get {
            onSuccess(userRepository.selectUsers()) {
              result => complete(result.asJson)
            }
          }
        } ~
          path("new") {
//            user: User =>
//              post {
//                onSuccess(userRepository.create(user)) {
//                  result => complete(StatusCodes.OK)
//                }
//
//              }
            complete("added")
          } ~
          pathPrefix(LongNumber) { id =>
            post {
              complete("user updated")
            } ~
              get {
                pathPrefix("visits") {
                  onSuccess(visitRepository.getVisitsByUser(id)) {
                    result => complete(result.asJson)
                  }
                } ~
                  onSuccess(userRepository.getById(id)) {
                    result => complete(result.asJson)
                  }
              }
          }
      } ~
      pathPrefix("locations") {
        pathSingleSlash {
          get {
            onSuccess(locationRepository.selectLocations()) {
              result => complete(result.asJson)
            }
          }
        } ~
          path("new") {
            post {
              complete("location added")
            }
          } ~
          pathPrefix(LongNumber) { id =>
              post {
//                entity(as[Location]){
//                location => {
//                  onSuccess(locationRepository.update(location)) {
//                    result => complete(result.asJson)
//                  }
//                }
                complete("location updated")
              } ~
              get {
                pathPrefix("avg") {
                  onSuccess(visitRepository.getVisitsMarks(id)) {
                    result => complete(result.asJson)
                  }
                } ~
                  onSuccess(locationRepository.getById(id)) {
                    result => complete(result.asJson)
                  }
              }
          }
      } ~
      pathPrefix("visits") {
        pathSingleSlash {
          get {
            onSuccess(visitRepository.selectVisits()) {
              result => complete(result.asJson)
            }
          }
        } ~
          path("new") {
            post {
              complete("visit added")
            }
          } ~
          pathPrefix(LongNumber) { id =>
            post {
              complete("visit updated")
            } ~
              get {
                onSuccess(visitRepository.getById(id)) {
                  result => complete(result.asJson)
                }
              }
          }
      }
}
