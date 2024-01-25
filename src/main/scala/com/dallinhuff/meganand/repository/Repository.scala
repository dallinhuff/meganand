package com.dallinhuff.meganand.repository

import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.*

trait Repository[A]:
  def create(el: A): Task[A]
  def read(id: Long): Task[Option[A]]
  def readAll: Task[List[A]]
  def update(id: Long, fn: A => A): Task[A]
  def delete(id: Long): Task[A]

object Repository:
  def quillLayer =
    Quill.Postgres.fromNamingStrategy(SnakeCase)
  def dataSourceLayer =
    Quill.DataSource.fromPrefix("dallinhuff.db")
  val dataLayer =
    dataSourceLayer >>> quillLayer
