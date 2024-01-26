package com.dallinhuff.meganand.model.request

import zio.json.JsonCodec

/** Request body to create a new address model */
final case class CreateAddressRequest(
    firstName: String,
    lastName: String,
    streetAddress: String,
    apartment: Option[String],
    city: String,
    state: String,
    zipCode: String,
    country: String
) derives JsonCodec
