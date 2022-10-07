package repo

import model._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}

class PersonRepo(implicit mongoClient: MongoClient) {
  val personCodecProvider: CodecRegistry = fromProviders(classOf[Person])
  val codecRegistry: CodecRegistry = fromRegistries(personCodecProvider, DEFAULT_CODEC_REGISTRY)
  val database: MongoDatabase = mongoClient.getDatabase("my_database").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Person] = database.getCollection("my_collection")

  def selectPersonWhereFirstNameIs(name: String): SingleObservable[Person] = {
    collection.find(equal("firstName", name)).first()
  }

  def createANew(person: Person): SingleObservable[InsertOneResult] = {
    collection.insertOne(person)
  }

  def updatePersonWhere(key: String, person: Person): SingleObservable[UpdateResult] = {
    collection.updateOne(
      equal("firstName", key),
      combine(
        set("firstName",  person.firstName),
        set("lastName",   person.lastName)
      ))
  }

  def deletePersonWhere(firstName: String): SingleObservable[DeleteResult] = {
    collection.deleteOne(equal("firstName", firstName))
  }
}
