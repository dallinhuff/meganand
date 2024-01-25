package com.dallinhuff.meganand.http.controller

import com.dallinhuff.meganand.http.endpoint.AddressEndpoints
import com.dallinhuff.meganand.service.AddressService
import sttp.tapir.server.ServerEndpoint
import zio.*

class AddressController private (addressService: AddressService)
    extends Controller
    with AddressEndpoints:
  val create: ServerEndpoint[Any, Task] =
    createEndpoint.serverLogic(addressService.create(_).either)
  val read: ServerEndpoint[Any, Task] =
    readEndpoint.serverLogic(addressService.read(_).either)
  val readAll: ServerEndpoint[Any, Task] =
    readAllEndpoint.serverLogic(_ => addressService.readAll.either)
  val delete: ServerEndpoint[Any, Task] =
    deleteEndpoint.serverLogic(addressService.delete(_).either)

  override val routes: List[ServerEndpoint[Any, Task]] = List(
    create,
    read,
    readAll,
    delete
  )
  
object AddressController:
  val makeZIO = for
    addressService <- ZIO.service[AddressService]
  yield AddressController(addressService)
