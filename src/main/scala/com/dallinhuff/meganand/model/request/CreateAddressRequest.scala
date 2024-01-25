package com.dallinhuff.meganand.model.request

import zio.json.JsonCodec

case class CreateAddressRequest(
    firstName: String,
    lastName: String,
    streetAddress: String,
    apartment: Option[String],
    city: String,
    state: String,
    zipCode: String,
    country: String
) derives JsonCodec
