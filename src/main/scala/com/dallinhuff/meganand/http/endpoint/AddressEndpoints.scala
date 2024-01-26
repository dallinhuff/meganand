package com.dallinhuff.meganand.http.endpoint

import com.dallinhuff.meganand.model.data.Address
import com.dallinhuff.meganand.model.request.CreateAddressRequest
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

/** Address endpoints to be handled by a controller */
trait AddressEndpoints extends Endpoints:
  /** endpoint create a new address posting */
  val createEndpoint: BasicEP[CreateAddressRequest, Address] =
    baseEndpoint
      .tag("address")
      .name("create")
      .description("create a new address")
      .in("address")
      .post
      .in(jsonBody[CreateAddressRequest])
      .out(jsonBody[Address])

  /** endpoint to get an address by id */
  val readEndpoint: SecureEP[Long, Option[Address]] =
    secureEndpoint
      .tag("address")
      .name("read")
      .description("read an address by id")
      .in("address" / path[Long]("id"))
      .get
      .out(jsonBody[Option[Address]])

  /** endpoint to get all addresses */
  val readAllEndpoint: SecureEP[Unit, List[Address]] =
    secureEndpoint
      .tag("address")
      .name("read")
      .description("read all addresses")
      .in("address")
      .get
      .out(jsonBody[List[Address]])

  /** endpoint to delete an existing address */
  val deleteEndpoint: SecureEP[Long, Address] =
    secureEndpoint
      .tag("address")
      .name("update")
      .description("delete an existing address")
      .in("address" / path[Long]("id"))
      .delete
      .out(jsonBody[Address])
