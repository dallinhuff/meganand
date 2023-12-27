package com.dallinhuff.meganand
package http.controller

import sttp.tapir.server.ServerEndpoint
import zio.{Task, URIO}

trait Controller:
  val routes: List[ServerEndpoint[Any, Task]]
  
trait ControllerZIO[C <: Controller, S]:
  def makeZIO: URIO[S, C]
