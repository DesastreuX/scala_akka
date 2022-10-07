scalaVersion := "2.13.8"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.10"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
)
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.6.0"