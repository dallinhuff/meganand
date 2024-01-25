package com.dallinhuff.meganand.model.data

import zio.json.JsonCodec

case class UserId(id: Long, email: String) derives JsonCodec
