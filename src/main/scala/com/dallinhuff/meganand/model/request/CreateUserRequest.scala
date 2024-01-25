package com.dallinhuff.meganand.model.request

import zio.json.JsonCodec

case class CreateUserRequest(email: String, password: String) derives JsonCodec
