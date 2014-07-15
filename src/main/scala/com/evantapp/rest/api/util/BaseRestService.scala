package com.evantapp.rest.api.util

import akka.actor.ActorRefFactory
import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport
import spray.routing.HttpService

/**
 * All traits that have routes extend this trait.
 * It has the executionContext & the DefaultFormats for json marshalling.
 */
trait BaseRestService extends HttpService with Json4sSupport {
  implicit def executionContext = actorRefFactory.dispatcher
  val json4sFormats = DefaultFormats
}