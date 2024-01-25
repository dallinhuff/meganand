package com.dallinhuff.meganand.http.endpoint

import com.dallinhuff.meganand.model.data.{UserId, UserToken}
import com.dallinhuff.meganand.model.request.{CreateUserRequest, LoginRequest}
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

trait UserEndpoints extends Endpoints:
  val registerEndpoint =
    baseEndpoint
      .tag("user")
      .name("register")
      .description("register a new user")
      .in("user")
      .post
      .in(jsonBody[CreateUserRequest])
      .out(jsonBody[UserId])
    
  val loginEndpoint =
    baseEndpoint
      .tag("user")
      .name("login")
      .description("login as an existing user")
      .in("user" / "login")
      .post
      .in(jsonBody[LoginRequest])
      .out(jsonBody[UserToken])
