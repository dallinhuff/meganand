package com.dallinhuff.meganand.model.data

import zio.json.JsonCodec

case class Address(
    id: Long,
    firstName: String,
    lastName: String,
    streetAddress: String,
    apartment: Option[String],
    city: String,
    state: String,
    zipCode: String,
    country: String
) derives JsonCodec
