package com.dallinhuff.meganand
package repository

import model.Address

import zio.*
import io.getquill.*
import io.getquill.jdbczio.Quill

trait AddressRepository extends Repository[Address]

class AddressRepositoryLive private (quill: Quill.Postgres[SnakeCase]) extends AddressRepository:
  import quill.*
  
  inline given schema: SchemaMeta[Address] = schemaMeta[Address]("addresses")
  inline given insMeta: InsertMeta[Address] = insertMeta[Address](_.id)
  inline given upMeta: UpdateMeta[Address] = updateMeta[Address](_.id)
  
  override def create(address: Address): Task[Address] =
    run(query[Address].insertValue(lift(address)).returning(a => a))

  override def getById(id: Long): Task[Option[Address]] =
    run(query[Address].filter(_.id == lift(id))).map(_.headOption)
  
  override def getAll: Task[List[Address]] =
    run(query[Address])

  override def delete(id: Long): Task[Address] =
    run(query[Address].filter(_.id == lift(id)).delete.returning(a => a))
  
object AddressRepositoryLive:
  val layer: ZLayer[Quill.Postgres[SnakeCase], Nothing, AddressRepository] =
    ZLayer:
      ZIO.service[Quill.Postgres[SnakeCase]]
        .map(new AddressRepositoryLive(_))
