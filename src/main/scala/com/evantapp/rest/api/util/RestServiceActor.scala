package com.evantapp.rest.api.util

import akka.actor.Actor
import com.evantapp.rest.api.UsersRestService

/**
 * Actor that uses the routes and generates the service
 * If you want to add a new "MyRouteService" you have
 * to add the trait with the "with" keyword and then modify the runRoute(usersRoute)
 * with runRoute(usersRoute ~ myRoute)
 *
 * Example:
 * class RestServiceActor extends Actor with UserRestService with MyRouteService {
 *   def actorRefFactory = context
 *   def receive = runRoute(usersRoute ~ myRoutes)
 * }
 *
 */

class RestServiceActor extends Actor with UsersRestService {
  def actorRefFactory = context
  def receive = runRoute(usersRoute)
}