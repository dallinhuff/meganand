package com.dallinhuff.meganand.http.controller

import com.dallinhuff.meganand.http.endpoint.AddressEndpoints
import com.dallinhuff.meganand.model.data.UserId
import com.dallinhuff.meganand.service.{AddressService, JwtService}
import sttp.tapir.server.ServerEndpoint
import zio.*

class AddressController private (
    addressService: AddressService,
    jwtService: JwtService
) extends Controller
    with AddressEndpoints:
  val create: ServerEndpoint[Any, Task] =
    createEndpoint.serverLogic(addressService.create(_).either)

  val read: ServerEndpoint[Any, Task] =
    readEndpoint
      .serverSecurityLogic[UserId, Task](jwtService.verifyToken(_).either)
      .serverLogic(_ => id => addressService.read(id).either)

  val readAll: ServerEndpoint[Any, Task] =
    readAllEndpoint
      .serverSecurityLogic[UserId, Task](jwtService.verifyToken(_).either)
      .serverLogic(_ => _ => addressService.readAll.either)

  val delete: ServerEndpoint[Any, Task] =
    deleteEndpoint
      .serverSecurityLogic[UserId, Task](jwtService.verifyToken(_).either)
      .serverLogic(_ => id => addressService.delete(id).either)

  override val routes: List[ServerEndpoint[Any, Task]] = List(
    create,
    read,
    readAll,
    delete
  )

object AddressController:
  val makeZIO = for
    addressService <- ZIO.service[AddressService]
    jwtService     <- ZIO.service[JwtService]
  yield AddressController(addressService, jwtService)
