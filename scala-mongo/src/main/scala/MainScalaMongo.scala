import com.sun.tools.javac.util.Assert.error
import model.Person
import repo.PersonRepo
import org.mongodb.scala.{MongoClient, MongoCollection, Observer, SingleObservable, Subscription}

import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.{Failure, Success}

object MainScalaMongo extends App {
  implicit val mongoClient: MongoClient = MongoClient()
  val repo = new PersonRepo
  val mayBeANewPerson: SingleObservable[InsertOneResult] = repo.createANew(Person("Scala", "Awesome"))
  // mayBeANewPerson.subscribe(new Observer[InsertOneResult] {
  //   override def onNext(result: InsertOneResult): Unit = println(s"On Next $result")
  //   override def onComplete(): Unit = println("Insert successfully")
  //   override def onError(e: Throwable): Unit = println(s"Error $e")
  // })
  mayBeANewPerson.toFuture().onComplete() match {
    case Success(result) => println(result)
    case Failure(e) => println(s"Error $e")
  }
  
  Thread.sleep(200)
  println("Hello World!")
}
