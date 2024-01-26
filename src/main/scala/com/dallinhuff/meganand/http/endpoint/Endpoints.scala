package com.dallinhuff.meganand.http.endpoint

import sttp.tapir.*
import com.dallinhuff.meganand.model.error.HttpError

/** Base Tapir endpoint implementations */
trait Endpoints:
  protected type BasicEP[I, O] = Endpoint[Unit, I, Throwable, O, Any]
  protected type SecureEP[I, O] = Endpoint[String, I, Throwable, O, Any]
  
  /** Basic endpoint that maps ApplicationErrors to HttpErrors with correct
    * status codes
    */
  val baseEndpoint: BasicEP[Unit, Unit] =
    endpoint
      .errorOut(statusCode and plainBody[String])
      .mapErrorOut[Throwable](HttpError.decode)(HttpError.encode)

  /** Modified base endpoint that requires an auth bearer header */
  val secureEndpoint: SecureEP[Unit, Unit] =
    baseEndpoint
      .securityIn(auth.bearer[String]())
