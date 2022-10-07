import org.mongodb.scala.{MongoClient, MongoDatabase, MongoCollection, Observer, SingleObservable, Subscription}
import scala.concurrent.Future
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala.model.Filters._

class OrderMongoRepository {
    // TODO: Copy OrderRepository but using MongoDB
    implicit val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("order_db")
    val collection: MongoCollection[Document] = database.getCollection("order_collection")
    def saveItem(item: Item): Future[InsertOneResult] = {
        val doc: Document = Document("item" -> Document("name" -> item.name, "id" -> item.id))
        collection.insertOne(doc).toFuture
    }
    def findById(id: Long): Future[Document] = {
        collection.find(equal("item.id", id)).first.toFuture
    }
}
