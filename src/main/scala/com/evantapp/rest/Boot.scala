package com.evantapp.rest

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import com.evantapp.rest.config.Configuration
import com.evantapp.rest.api.util.RestServiceActor

object Boot extends App with Configuration {
  // we need an ActorSystem to host our application
  implicit val system = ActorSystem("rest-system")

  // create and start our service actor
  val service = system.actorOf(Props[RestServiceActor], "rest-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = serviceHost, port = servicePort)
}