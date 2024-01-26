package com.dallinhuff.meganand.http.controller

import com.dallinhuff.meganand.http.endpoint.UserEndpoints
import com.dallinhuff.meganand.model.data.UserId
import com.dallinhuff.meganand.service.{JwtService, UserService}
import sttp.tapir.server.ServerEndpoint
import zio.*

/** Http Controller for handling user routes
  * @param userService
  *   service layer for user business logic
  * @param jwtService
  *   service layer for JWT verification/generation
  */
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
  val makeZIO: URIO[UserService & JwtService, UserController] = for
    userService <- ZIO.service[UserService]
    jwtService  <- ZIO.service[JwtService]
  yield UserController(userService, jwtService)
