package com.dallinhuff.meganand
package model

import zio.json.{DeriveJsonCodec, JsonCodec}

case class Address(
  id: Long,
  name: String,
  line1: String,
  line2: Option[String],
  city: String,
  state: String,
  zip: String
)

object Address:
  given codec: JsonCodec[Address] =
    DeriveJsonCodec.gen[Address]
