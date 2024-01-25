package com.dallinhuff.meganand.http.controller

import sttp.tapir.server.ServerEndpoint
import zio.Task

trait Controller:
  val routes: List[ServerEndpoint[Any, Task]]
