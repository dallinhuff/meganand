package com.dallinhuff.meganand.http.endpoint

import com.dallinhuff.meganand.model.data.{UserId, UserToken}
import com.dallinhuff.meganand.model.request.{CreateUserRequest, LoginRequest}
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

/** User Tapir endpoints to be handled by a controller */
trait UserEndpoints extends Endpoints:
  /** Endpoint for registering a new user */
  val registerEndpoint: BasicEP[CreateUserRequest, UserId] =
    baseEndpoint
      .tag("user")
      .name("register")
      .description("register a new user")
      .in("user")
      .post
      .in(jsonBody[CreateUserRequest])
      .out(jsonBody[UserId])

  /** Endpoint for logging in as an existing user */
  val loginEndpoint: BasicEP[LoginRequest, UserToken] =
    baseEndpoint
      .tag("user")
      .name("login")
      .description("login as an existing user")
      .in("user" / "login")
      .post
      .in(jsonBody[LoginRequest])
      .out(jsonBody[UserToken])
