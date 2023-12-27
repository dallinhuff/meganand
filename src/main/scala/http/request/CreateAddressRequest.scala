package com.dallinhuff.meganand
package http.request

import model.Address

import zio.json.{DeriveJsonCodec, JsonCodec}

/**
 * request body for creating an address
 */
case class CreateAddressRequest(
  name: String,
  line1: String,
  line2: Option[String],
  city: String,
  state: String,
  zip: String
):
  def toAddress(id: Long = -1): Address =
    Address(id, name, line1, line2, city, state, zip)

object CreateAddressRequest:
  given codec: JsonCodec[CreateAddressRequest] =
    DeriveJsonCodec.gen[CreateAddressRequest]