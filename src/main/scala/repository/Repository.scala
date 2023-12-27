package com.dallinhuff.meganand
package repository

import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{Task, ZLayer}

import javax.sql.DataSource

trait Repository[A]:
  def create(item: A): Task[A]
  def getById(id: Long): Task[Option[A]]
  def getAll: Task[List[A]]
  def delete(id: Long): Task[A]

object Repository:
  def quillLayer: ZLayer[DataSource, Nothing, Quill.Postgres[SnakeCase.type]] =
    Quill.Postgres.fromNamingStrategy(SnakeCase)
  def dataSourceLayer: ZLayer[Any, Throwable, DataSource] =
    Quill.DataSource.fromPrefix("dallinhuff.db")
  val dataLayer: ZLayer[Any, Throwable, Quill.Postgres[SnakeCase.type]] =
    dataSourceLayer >>> quillLayer