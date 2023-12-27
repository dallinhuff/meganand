package com.dallinhuff.meganand
package http

import http.controller.AddressController
import service.AddressService

import sttp.tapir.server.ServerEndpoint
import zio.{Task, URIO, ZIO}

/**
 * public api for http app
 * wires together controller routes into a single list of routes
 */
object HttpApi:

  // list of controllers to include
  private val controllersZIO =
    for
      address <- AddressController.makeZIO
    yield List(address)

  val endpointsZIO: URIO[AddressService, List[ServerEndpoint[Any, Task]]] =
    controllersZIO.flatMap(c => ZIO.succeed(c.flatMap(_.routes)))
