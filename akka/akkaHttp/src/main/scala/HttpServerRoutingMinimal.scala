import akka.actor.typed.ActorSystem
import akka.actor
import akka.http.scaladsl.model._
import actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import scala.io.StdIn

object HttpServerRoutingMinimal {
    def main(params: Array[String]) = {
        implicit val system = ActorSystem(Behaviors.empty, "my-system")
        implicit val executionContext = system.executionContext
        
        val route = new Routing(new OrderRepository)

        val bindingFuture = Http().newServerAt("localhost", 8080).bind(route.marshallRoute())
        println(s"Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
        StdIn.readLine() // let it run until user presses return
        bindingFuture
            .flatMap(_.unbind()) // trigger unbinding from the port
            .onComplete(_ => system.terminate()) // and shutdown when done
    }
}
