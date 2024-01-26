package com.dallinhuff.meganand

import com.dallinhuff.meganand.config.*
import com.dallinhuff.meganand.http.HttpApi
import com.dallinhuff.meganand.repository.*
import com.dallinhuff.meganand.service.*
import sttp.tapir.server.interceptor.cors.CORSInterceptor
import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import zio.{Task, ZIO, ZIOAppDefault}
import zio.http.Server

/** The main entry point of the application */
object Application extends ZIOAppDefault:
  private val serverProgram = for
    endpoints <- HttpApi.endpointsZIO
    options <- ZIO.succeed:
      ZioHttpServerOptions.default
        .appendInterceptor(CORSInterceptor.default)
    _ <- Server.serve:
      ZioHttpInterpreter(options)
        .toHttp(endpoints)
        .withDefaultErrorResponse
  yield ()

  override def run: Task[Unit] =
    serverProgram.provide(
      // server
      Server.default,
      // configuration
      Configs.makeLayer[JwtConfig]("dallinhuff.jwt"),
      // services
      AddressServiceLive.layer,
      UserServiceLive.layer,
      JwtServiceLive.layer,
      // repo
      AddressRepositoryLive.layer,
      UserRepositoryLive.layer,
      Repository.dataLayer
    )
