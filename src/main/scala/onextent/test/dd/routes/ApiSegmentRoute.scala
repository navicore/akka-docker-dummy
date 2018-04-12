package onextent.test.dd.routes

import java.util.Date

import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging
import onextent.test.dd.ErrorSupport
import onextent.test.dd.models.{JsonSupport, Message}
import spray.json._

object ApiSegmentRoute
    extends JsonSupport
    with LazyLogging
    with Directives
    with ErrorSupport {

  def apply: Route =
    path(urlpath / Segment) { name =>
      logRequest(s"$urlpath / $name") {
        handleErrors {
          cors(corsSettings) {
            get {
              val response =
                Message(java.util.UUID.randomUUID(), new Date(), s"hiya $name")
              complete(response.toJson.prettyPrint)
            } ~
              post {
                decodeRequest {
                  entity(as[Message]) { m =>
                    val response = Message(java.util.UUID.randomUUID(),
                                           new Date(),
                                           s"${m.body} to you, too!")
                    complete(response.toJson.prettyPrint)
                  }
                }
              }
          }
        }
      }
    }
}
