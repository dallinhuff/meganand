package com.dallinhuff.meganand.model.data

import zio.json.JsonCodec

case class User(
    id: Long,
    email: String,
    hashedPassword: String
) derives JsonCodec
