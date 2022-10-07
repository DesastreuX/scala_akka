import akka.http.scaladsl.model._
import akka.http.scaladsl.Http
import scala.io.StdIn

object HttpServerRoutingMinimal extends App with AkkaComponent with RepositoryComponent with RoutingComponent {
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route.getRoute())
    println(s"Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
        .flatMap(_.unbind()) // trigger unbinding from the port
        .onComplete(_ => system.terminate()) // and shutdown when done
}
