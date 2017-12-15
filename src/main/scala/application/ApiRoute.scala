package application

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import models.{Location, User, Visit}

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
            post{
              entity(as[User]) {
                user => {
                  onSuccess(userRepository.create(user)) {
                    result => complete(StatusCodes.OK)
                  }
                }
              }
            }

          } ~
          pathPrefix(LongNumber) { id =>
            post {
              entity(as[User]) {
                user => {
                  onSuccess(userRepository.update(user)) {
                    result => complete(StatusCodes.OK)
                  }
                }
              }
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
              entity(as[Location]) {
                location => {
                  onSuccess(locationRepository.create(location)) {
                    result => complete(StatusCodes.OK)
                  }
                }
              }
            }
          } ~
          pathPrefix(LongNumber) { id =>
            post {
              entity(as[Location]) {
                location => {
                  onSuccess(locationRepository.update(location)) {
                    result => complete(result.asJson)
                  }
                }
              }
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
              entity(as[Visit]) {
                visit => {
                  onSuccess(visitRepository.create(visit)) {
                    result => complete(StatusCodes.OK)
                  }
                }
              }
            }
          } ~
          pathPrefix(LongNumber) { id =>
            post {
              entity(as[Visit]) {
                visit => {
                  onSuccess(visitRepository.update(visit)) {
                    result => complete(result.asJson)
                  }
                }
              }
            } ~
              get {
                onSuccess(visitRepository.getById(id)) {
                  result => complete(result.asJson)
                }
              }
          }
      }
}
