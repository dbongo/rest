package com.evantapp.rest

import spray.routing.SimpleRoutingApp
import akka.actor.ActorSystem
import reactivemongo.api.MongoDriver
import spray.httpx.PlayJsonSupport
import reactivemongo.bson.BSONObjectID
import com.evantapp.rest.model.{Users, User}
import scala.concurrent.ExecutionContext.Implicits._
import play.api.libs.json.Json
import spray.http.{StatusCodes, HttpResponse}
import com.evantapp.rest.routing.BSONPathMatcher._

object Main extends App with SimpleRoutingApp with PlayJsonSupport {

  implicit val system = ActorSystem("rest-system")

  val driver = new MongoDriver(system)

  implicit val db = driver.connection(Seq("localhost")).db("test")

  val userRestRoutes = pathPrefix("user") {
    pathEnd {
      post {
        entity(as[User]) { user =>
          complete(Users.insert(user))
        }
      } ~
      get {
        complete(Users.findAll().map(u => Json.obj("users" -> u)))
      }
    } ~
    path(bsonId) { id =>
      get {
        complete(Users.findOneById(id))
      } ~
      put {
        entity(as[User]) { user =>
          complete(Users.update(user))
        }
      } ~
      delete {
        complete(Users.delete(id).map(_ => HttpResponse(StatusCodes.OK)))
      }
    }
  }

  startServer("localhost", 9090) {
    pathSingleSlash {
      complete("hello world")
    } ~ userRestRoutes
  }
}