package com.evantapp.rest.api

import com.evantapp.rest.domain.{generateUsers, User}
import com.evantapp.rest.api.util.BaseRestService

/**
 * Trait that has the routes
 * and what to do following the path
 */
trait UsersRestService extends BaseRestService {

  def usersRoute = usersById ~ allUsers ~ postUser

  val usersById = get {
    path("users" / LongNumber) { id: Long =>
      complete(generateUsers.users.find(_.id.get == id))
    }
  }

  val allUsers = get {
    path("users") {
      complete(generateUsers.users)
    }
  }

  val postUser = post {
    path("users") {
      entity(as[User]) { newUser =>
        generateUsers.users += newUser
        complete(generateUsers.users)
      }
    }
  }
}