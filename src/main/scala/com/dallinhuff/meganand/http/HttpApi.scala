package com.dallinhuff.meganand.http

import com.dallinhuff.meganand.http.controller.*
import com.dallinhuff.meganand.service.{AddressService, JwtService, UserService}
import sttp.tapir.server.ServerEndpoint
import zio.{Task, ZIO}

/** API for http portion of main application */
object HttpApi:
  private type R = AddressService & UserService & JwtService

  /** all endpoints in the http app (wrapped in ZIO URIO) */
  val endpointsZIO: ZIO[R, Nothing, List[ServerEndpoint[Any, Task]]] =
    makeControllers.map(_.flatMap(_.routes))

  private def makeControllers = for
    addressController <- AddressController.makeZIO
    userController    <- UserController.makeZIO
  yield List(addressController, userController)
