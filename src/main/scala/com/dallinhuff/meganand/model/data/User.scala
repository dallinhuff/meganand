package com.dallinhuff.meganand.model.data

import zio.json.JsonCodec

/** A privileged/logged-in user */
final case class User(
    id: Long,
    email: String,
    hashedPassword: String
) derives JsonCodec
