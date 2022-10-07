import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.actor

trait AkkaComponent {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext
}
