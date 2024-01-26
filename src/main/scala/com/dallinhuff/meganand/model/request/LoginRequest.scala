package com.dallinhuff.meganand.model.request

import zio.json.JsonCodec

/** request to log in as an existing user */
final case class LoginRequest(email: String, password: String) derives JsonCodec
