import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Route
import akka.Done
import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.Future
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import spray.json.DefaultJsonProtocol._
import scala.io.StdIn
import akka.http.scaladsl.Http

import org.mongodb.scala.result.InsertOneResult
import scala.util.{Failure, Success}
import akka.http.scaladsl.model.StatusCodes._
import org.mongodb.scala.bson.collection.immutable.Document
// class Routing(orderRepo: OrderRepository) {
//     def route() =
//         path("hello") {
//             get {
//                 complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
//             }
//         }

//     // formats for unmarshalling and marshalling
//     implicit val itemFormat = jsonFormat2(Item)
//     implicit val orderFormat = jsonFormat1(Order)
    
//     def marshallRoute() =
//         concat(
//             get {
//                 pathPrefix("item" / LongNumber) { id =>
//                     // there might be no item for a given id
//                     val maybeItem: Future[Option[Item]] = orderRepo.fetchItem(id)

//                     onSuccess(maybeItem) {
//                     case Some(item) => complete(item)
//                     case None       => complete(StatusCodes.NotFound)
//                     }
//                 }
//             },
//                 post {
//                     path("item") {
//                         entity(as[Item]) { item =>
//                             val saved: Future[Done] = orderRepo.saveOrder(item)
//                             onSuccess(saved) { _ => // we are not interested in the result value `Done` but only in the fact that it was successful
//                                 complete("order created")
//                         }
//                     }
//                 }
//             }
//         )
// }

class Routing(implicit mongoRepo: OrderMongoRepository) {
    implicit val itemFormat = jsonFormat2(Item)
    def getRoute() = concat(
        post {
            path("item") {
                entity(as[Item]) { item =>
                    val maybeSaved: Future[InsertOneResult] = mongoRepo.saveItem(item)
                    onComplete(maybeSaved) {
                        case Success(result) => complete(StatusCodes.OK, "ok")
                        case Failure(e) => complete(StatusCodes.NotFound, "nopeee")
                    }
                }
            }
        },
        get {
            pathPrefix("item" / LongNumber) { id =>
                // there might be no item for a given id
                val maybeItem: Future[Document] = mongoRepo.findById(id)

                onComplete(maybeItem) {
                    case Success(result) => complete(StatusCodes.OK, result.toJson)
                    case Failure(e) => complete(StatusCodes.NotFound, "nopeee")
                }
            }
        }
    )
}
