package com.dallinhuff.meganand.model.request

import zio.json.JsonCodec

/** request to create a new user model */
final case class CreateUserRequest(
    email: String,
    password: String
) derives JsonCodec
