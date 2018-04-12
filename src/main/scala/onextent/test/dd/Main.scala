package onextent.test.dd

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import onextent.test.dd.models.{JsonSupport, Message}
import onextent.test.dd.routes.{ApiRoute, ApiSegmentRoute}
import scala.concurrent.ExecutionContextExecutor

object Main extends LazyLogging with JsonSupport with ErrorSupport {

  def main(args: Array[String]) {

    implicit val system: ActorSystem = ActorSystem("AkkaDockerDummy-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route =
      HealthCheck ~
      ApiRoute.apply ~
      ApiSegmentRoute.apply

    Http().bindAndHandle(route, "0.0.0.0", port)
  }
}

