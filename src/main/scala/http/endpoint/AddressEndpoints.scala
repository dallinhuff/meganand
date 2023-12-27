package com.dallinhuff.meganand
package http.endpoint

import http.request.CreateAddressRequest
import model.Address

import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

/**
 * endpoints for address CRUD ops
 */
trait AddressEndpoints:
  val createEndpoint: Endpoint[Unit, CreateAddressRequest, Unit, Address, Any] =
    endpoint
      .tag("address")
      .name("create")
      .description("create an address entry")
      .in("addresses")
      .post
      .in(jsonBody[CreateAddressRequest])
      .out(jsonBody[Address])
    
  val getByIdEndpoint: Endpoint[Unit, Long, Unit, Option[Address], Any] =
    endpoint
      .tag("address")
      .name("create")
      .description("get an address by id")
      .in("addresses" / path[Long]("id"))
      .get
      .out(jsonBody[Option[Address]])
    
  val getAllEndpoint: Endpoint[Unit, Unit, Unit, List[Address], Any] =
    endpoint
      .tag("address")
      .name("getAll")
      .description("get all addresses")
      .in("addresses")
      .get
      .out(jsonBody[List[Address]])
  
  val deleteEndpoint: Endpoint[Unit, Long, Unit, Address, Any] =
    endpoint
      .tag("address")
      .name("delete")
      .description("delete an address")
      .in("addresses" / path[Long]("id"))
      .delete
      .out(jsonBody[Address])
