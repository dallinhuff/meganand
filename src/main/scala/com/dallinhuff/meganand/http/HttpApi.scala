package com.dallinhuff.meganand.http

import com.dallinhuff.meganand.http.controller.{AddressController, UserController}

object HttpApi:
  val endpointsZIO = makeControllers.map(_.flatMap(_.routes))
    
  private def makeControllers = for
    addressController <- AddressController.makeZIO
    userController <- UserController.makeZIO
  yield List(addressController, userController)
