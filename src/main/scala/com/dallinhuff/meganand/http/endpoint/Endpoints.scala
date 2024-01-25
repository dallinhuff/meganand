package com.dallinhuff.meganand.http.endpoint

import sttp.tapir.*
import com.dallinhuff.meganand.model.error.HttpError

trait Endpoints:
  val baseEndpoint: Endpoint[Unit, Unit, Throwable, Unit, Any] =
    endpoint
      .errorOut(statusCode and plainBody[String])
      .mapErrorOut[Throwable](HttpError.decode)(HttpError.encode)
    
  val secureEndpoint: Endpoint[String, Unit, Throwable, Unit, Any] =
    baseEndpoint
      .securityIn(auth.bearer[String]())
