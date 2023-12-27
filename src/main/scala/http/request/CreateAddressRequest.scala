package com.dallinhuff.meganand
package http.request

import zio.json.{DeriveJsonCodec, JsonCodec}

case class CreateAddressRequest(
  name: String,
  line1: String,
  line2: Option[String],
  city: String,
  state: String,
  zip: String
)

object CreateAddressRequest:
  given codec: JsonCodec[CreateAddressRequest] =
    DeriveJsonCodec.gen[CreateAddressRequest]