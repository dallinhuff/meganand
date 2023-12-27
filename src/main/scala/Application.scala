package com.dallinhuff.meganand

import http.HttpApi
import repository.{AddressRepositoryLive, Repository}
import service.AddressServiceLive

import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server

/**
 * entry point for server application
 */
object Application extends ZIOAppDefault:
  private val program =
    for
      endpoints   <- HttpApi.endpointsZIO
      interpreter <- ZIO.succeed(ZioHttpInterpreter(ZioHttpServerOptions.default))
      app         <- ZIO.succeed(interpreter.toHttp(endpoints).withDefaultErrorResponse)
      _           <- Server.serve(app)
    yield ()

  override def run: Task[Unit] = program.provide(
    Server.default,
    AddressServiceLive.layer,
    AddressRepositoryLive.layer,
    Repository.dataLayer
  )

