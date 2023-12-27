package com.dallinhuff.meganand
package http.controller

import http.endpoint.AddressEndpoints
import service.AddressService

import sttp.tapir.server.ServerEndpoint
import zio.{Task, URIO, ZIO}

/**
 * the controller for handling address routes
 * @param service the service containing the business logic for addresses
 */
class AddressController private (service: AddressService) extends Controller with AddressEndpoints:
  override val routes: List[ServerEndpoint[Any, Task]] =
    List(
      createEndpoint.serverLogicSuccess(service.create),
      getByIdEndpoint.serverLogicSuccess(service.getById),
      getAllEndpoint.serverLogicSuccess(_ => service.getAll),
      deleteEndpoint.serverLogicSuccess(service.delete)
    )

object AddressController extends ControllerZIO[AddressController, AddressService]:
  override val makeZIO: URIO[AddressService, AddressController] =
    ZIO.service[AddressService].map(new AddressController(_))
