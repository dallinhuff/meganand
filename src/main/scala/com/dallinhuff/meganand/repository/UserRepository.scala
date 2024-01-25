package com.dallinhuff.meganand.repository

import com.dallinhuff.meganand.model.data.User
import com.dallinhuff.meganand.model.error.NotFoundError
import io.getquill.*
import io.getquill.jdbczio.Quill
import zio.*

trait UserRepository extends Repository[User]:
  def readByEmail(email: String): Task[Option[User]]

class UserRepositoryLive private (quill: Quill.Postgres[SnakeCase])
    extends UserRepository:
  import quill.*

  inline given schema: SchemaMeta[User] =
    schemaMeta[User]("app_user")

  inline given insMeta: InsertMeta[User] =
    insertMeta[User](_.id)

  inline given upMeta: UpdateMeta[User] =
    updateMeta[User](_.id)

  override def create(el: User): Task[User] =
    run:
      query[User]
        .insertValue(lift(el))
        .returning(u => u)

  override def read(id: Long): Task[Option[User]] =
    run(query[User].filter(_.id == lift(id)))
      .map(_.headOption)

  override def readByEmail(email: String): Task[Option[User]] =
    run(query[User].filter(_.email == lift(email)))
      .map(_.headOption)

  override def readAll: Task[List[User]] =
    run(query[User])

  override def update(id: Long, fn: User => User): Task[User] =
    for
      existing <- read(id).someOrFail:
        NotFoundError(s"Cannot update user with id $id. Doesn't exist")
      updated <- run:
        query[User]
          .updateValue(lift(fn(existing)))
          .returning(u => u)
    yield updated

  override def delete(id: Long): Task[User] =
    for
      _ <- read(id).someOrFail:
        NotFoundError(s"Cannot delete user with id $id. Doesn't exist")
      deleted <- run:
        query[User]
          .filter(_.id == lift(id))
          .delete
          .returning(u => u)
    yield deleted

object UserRepositoryLive:
  val layer = ZLayer:
    ZIO
      .service[Quill.Postgres[SnakeCase]]
      .map(UserRepositoryLive(_))
