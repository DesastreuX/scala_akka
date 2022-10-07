import akka.Done
import spray.json.DefaultJsonProtocol._
import scala.concurrent.Future
import scala.concurrent.ExecutionContextExecutor

class OrderRepository(implicit executionContext: ExecutionContextExecutor) {
    var orders: List[Item] = Nil

    // formats for unmarshalling and marshalling
    // implicit val itemFormat = jsonFormat2(Item)
    // implicit val orderFormat = jsonFormat1(Order)

    // (fake) async database query api
    def fetchItem(itemId: Long): Future[Option[Item]] = Future {
        orders.find(o => o.id == itemId)
    }
    def saveOrder(item: Item): Future[Done] = {
        orders = item match {
            case Item(_, _) => item :: orders
            case _            => orders
        }
        Future { Done }
    }
}
