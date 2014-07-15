package com.evantapp.rest.model

import reactivemongo.bson._
import play.api.libs.json._
import scala.concurrent.Future
import reactivemongo.bson.BSONInteger
import reactivemongo.api.DefaultDB
import reactivemongo.bson.BSONString
import reactivemongo.api.collections.default.BSONCollection
import scala.concurrent.ExecutionContext.Implicits._

case class User(id: Option[BSONObjectID], firstName: String, lastName: String, email: String, password: String)

object User {

  import com.evantapp.rest.libs.json.BSONReads._
  import com.evantapp.rest.libs.json.BSONWrites._

  implicit val userFormat: Format[User] = Json.format[User]

  implicit object UserWriter extends BSONDocumentWriter[User] {
    def write(user: User): BSONDocument = BSONDocument(
      "_id" -> user.id.getOrElse(BSONObjectID.generate),
      "firstName" -> BSONString(user.firstName),
      "lastName" -> BSONString(user.lastName),
      "email" -> BSONString(user.email),
      "password" -> BSONString(user.password)
    )
  }

  implicit object UserReader extends BSONDocumentReader[User] {
    def read(bson: BSONDocument): User = User(
      bson.getAs[BSONObjectID]("_id"),
      bson.getAs[String]("firstName").get,
      bson.getAs[String]("lastName").get,
      bson.getAs[String]("email").get,
      bson.getAs[String]("password").get
    )
  }
}

object Users {

  private def collection(implicit db: DefaultDB): BSONCollection = db("users")

  def delete(id: BSONObjectID)(implicit db: DefaultDB): Future[Unit] =
    collection.remove(BSONDocument("_id" -> id)).map(_ => ())

  def update(user: User)(implicit db: DefaultDB): Future[User] =
    collection.update(BSONDocument("_id" -> user.id.get), user).map(_ => user)

  def insert(user: User)(implicit db: DefaultDB): Future[User] = {
    val u = user.copy(id = Some(BSONObjectID.generate))
    collection.save(u).map(_ => u)
  }

  def findOneById(id: BSONObjectID)(implicit db: DefaultDB): Future[Option[User]] = {
    collection.find(BSONDocument("_id" -> id)).one[User]
  }

  def findAll()(implicit db: DefaultDB): Future[List[User]] =
    collection.find(BSONDocument()).cursor[User].collect[List]()
}