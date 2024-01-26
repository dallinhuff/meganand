package com.dallinhuff.meganand.http.controller

import sttp.tapir.server.ServerEndpoint
import zio.Task

/** Base http controller trait */
trait Controller:
  /** the routes this controller handles */
  val routes: List[ServerEndpoint[Any, Task]]
