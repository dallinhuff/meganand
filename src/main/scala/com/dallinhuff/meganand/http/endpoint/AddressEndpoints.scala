package com.dallinhuff.meganand.http.endpoint

import com.dallinhuff.meganand.model.data.Address
import com.dallinhuff.meganand.model.request.CreateAddressRequest
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

trait AddressEndpoints extends Endpoints:
  val createEndpoint =
    baseEndpoint
      .tag("address")
      .name("create")
      .description("create a new address")
      .in("address")
      .post
      .in(jsonBody[CreateAddressRequest])
      .out(jsonBody[Address])

  val readEndpoint =
    secureEndpoint
      .tag("address")
      .name("read")
      .description("read an address by id")
      .in("address" / path[Long]("id"))
      .get
      .out(jsonBody[Option[Address]])

  val readAllEndpoint =
    secureEndpoint
      .tag("address")
      .name("read")
      .description("read all addresses")
      .in("address")
      .get
      .out(jsonBody[List[Address]])

  val updateEndpoint =
    secureEndpoint
      .tag("address")
      .name("update")
      .description("update an existing address")
      .in("address" / path[Long]("id"))
      .patch
      .out(jsonBody[Address])
    
  val deleteEndpoint =
    secureEndpoint
      .tag("address")
      .name("update")
      .description("delete an existing address")
      .in("address" / path[Long]("id"))
      .delete
      .out(jsonBody[Address])