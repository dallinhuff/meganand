package com.dallinhuff.meganand.http.controller

import com.dallinhuff.meganand.http.endpoint.UserEndpoints
import com.dallinhuff.meganand.model.data.UserId
import com.dallinhuff.meganand.service.{JwtService, UserService}
import sttp.tapir.server.ServerEndpoint
import zio.*

class UserController private (
    userService: UserService,
    jwtService: JwtService
) extends Controller
    with UserEndpoints:
  val register: ServerEndpoint[Any, Task] =
    registerEndpoint.serverLogic: req =>
      userService
        .register(req)
        .map(u => UserId(u.id, u.email))
        .either

  val login: ServerEndpoint[Any, Task] =
    loginEndpoint.serverLogic: req =>
      userService
        .login(req.email, req.password)
        .either

  override val routes: List[ServerEndpoint[Any, Task]] = List(
    register,
    login
  )
  
object UserController:
  val makeZIO = for
    userService <- ZIO.service[UserService]
    jwtService <- ZIO.service[JwtService]
  yield UserController(userService, jwtService)
