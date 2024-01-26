package com.dallinhuff.meganand.repository

import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{Task, ZLayer}

/** Repository/data-access layer for a domain type
  * @tparam A
  *   the model/domain type
  */
trait Repository[A]:
  /** create a new instance of the model
    * @param el
    *   the model to create
    * @return
    *   the model inserted in the database
    */
  def create(el: A): Task[A]

  /** get an existing instance of the model by id
    * @param id
    *   the id to search by
    * @return
    *   an option of the model when it exists
    */
  def read(id: Long): Task[Option[A]]

  /** get all existing instances of the model
    * @return
    *   all found instances
    */
  def readAll: Task[List[A]]

  /** update an existing model
    * @param id
    *   the id to search by
    * @param fn
    *   how to transform the model
    * @return
    *   the transformed model
    */
  def update(id: Long, fn: A => A): Task[A]

  /** delete an existing model
    * @param id
    *   the id to search by
    * @return
    *   the deleted model
    */
  def delete(id: Long): Task[A]
end Repository

object Repository:
  private def quillLayer =
    Quill.Postgres.fromNamingStrategy(SnakeCase)
    
  private def dataSourceLayer =
    Quill.DataSource.fromPrefix("dallinhuff.db")
    
  val dataLayer: ZLayer[Any, Throwable, Quill.Postgres[SnakeCase.type]] =
    dataSourceLayer >>> quillLayer
