package com.dslplatform.json.example

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

//import implicit conversion for DSL-JSON Scala pimps
import com.dslplatform.json.DslJson

//when defaults are defined properties can be omitted from JSON - and default value will be used for them
//types without Option[_] can't be null in input JSON
case class Report(title: String, users: Seq[User] = Nil)

//Primitives in container are correctly analyzed and decoded
case class User(name: String, age: Option[Int], metadata: Map[String, Int] = Map.empty)

object Example extends App {

  //This configuration will not support unknown types (eg AnyRef,...) or Java8 specific types
  //To allow support for unknown types use new DslJson[Any](Settings.withRuntime())
  implicit val dslJson = new DslJson[Any]()

  //we can either put encoders on implicit scope, pass them in to encode/decode methods
  //or put dslJson on implicit scope
  //implicit val encoder = dslJson.encoder[Report]
  //implicit val decoder = dslJson.decoder[Report]

  val os = new ByteArrayOutputStream()
  val report = Report(
    "DSL-JSON serialization",
    Seq(
      User("username1", Some(55)),
      User("username2", Some(-123), Map("abc" -> 123)),
      User("username3", Some(0), Map("x" -> -1, "y" -> 1))
    )
  )
  //when using encode instead of serialize, types will be analyzed before conversion starts
  dslJson.encode(report, os)
  //alternative syntax when neither DslJson nor encoder is in implicit scope would be
  //beware that creating encoder allocates memory due to TypeTag allocation (even if encoder is already built and cached)
  //dslJson.encode(report, os)(dslJson.encoder)

  val is = new ByteArrayInputStream(os.toByteArray)
  //by using decode TypeTags will be used to create accurate type representation
  //otherwise some types - eg classes nested in objects are not analyzed correctly due to missing metadata
  val result = dslJson.decode[Report](is)
  //alternative syntax when neither DslJson nor decoder is in implicit scope would be
  //beware that creating decoder allocates memory due to TypeTag allocation
  //val result = dslJson.decode[Report](is)(dslJson.decoder)

  println(os)
  println(result == report)
}
