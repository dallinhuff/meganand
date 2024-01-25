package com.dallinhuff.meganand.repository

import com.dallinhuff.meganand.model.data.Address
import com.dallinhuff.meganand.model.error.NotFoundError
import io.getquill.*
import io.getquill.jdbczio.Quill
import zio.*

trait AddressRepository extends Repository[Address]

class AddressRepositoryLive private (quill: Quill.Postgres[SnakeCase])
    extends AddressRepository:
  import quill.*

  inline given schema: SchemaMeta[Address] =
    schemaMeta[Address]("address")
  inline given insMeta: InsertMeta[Address] =
    insertMeta[Address](_.id)
  inline given upMeta: UpdateMeta[Address] =
    updateMeta[Address](_.id)

  override def create(address: Address): Task[Address] =
    run:
      query[Address]
        .insertValue(lift(address))
        .returning(a => a)

  override def read(id: Long): Task[Option[Address]] =
    run(query[Address].filter(_.id == lift(id)))
      .map(_.headOption)

  override def readAll: Task[List[Address]] =
    run(query[Address])

  override def update(id: Long, fn: Address => Address): Task[Address] =
    for
      existing <- read(id).someOrFail:
        NotFoundError(s"Cannot update address with id $id. Doesn't exist")
      updated <- run:
        query[Address]
          .updateValue(lift(fn(existing)))
          .returning(a => a)
    yield updated

  override def delete(id: Long): Task[Address] =
    for
      _ <- read(id).someOrFail:
        NotFoundError(s"Cannot delete address with id $id. Doesn't exist")
      deleted <- run:
        query[Address]
          .filter(_.id == lift(id))
          .delete
          .returning(a => a)
    yield deleted

object AddressRepositoryLive:
  val layer = ZLayer:
    ZIO
      .service[Quill.Postgres[SnakeCase]]
      .map(AddressRepositoryLive(_))
