package com.dallinhuff.meganand.model.request

import zio.json.JsonCodec

case class LoginRequest(email: String, password: String) derives JsonCodec
