package com.dallinhuff.meganand
package http.controller

import sttp.tapir.server.ServerEndpoint
import zio.Task

trait BaseController:
  val routes: List[ServerEndpoint[Any, Task]]