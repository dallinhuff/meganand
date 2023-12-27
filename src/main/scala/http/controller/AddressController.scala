package com.dallinhuff.meganand
package http.controller

import http.endpoint.AddressEndpoints
import service.AddressService

import sttp.tapir.server.ServerEndpoint
import zio.Task

class AddressController private (service: AddressService) extends BaseController with AddressEndpoints:
  val create: ServerEndpoint[Any, Task] =
    createEndpoint.serverLogicSuccess: req =>
      ???

  val getById: ServerEndpoint[Any, Task] =
    getByIdEndpoint.serverLogicSuccess: id =>
      ???

  val getAll: ServerEndpoint[Any, Task] =
    getAllEndpoint.serverLogicSuccess(_ => ???)

  val delete: ServerEndpoint[Any, Task] =
    deleteEndpoint.serverLogicSuccess: id =>
      ???

  override val routes: List[ServerEndpoint[Any, Task]] =
    List(create, getById, getAll, delete)

object AddressController
