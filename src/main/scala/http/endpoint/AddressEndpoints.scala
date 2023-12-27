package com.dallinhuff.meganand
package http.endpoint

import http.request.CreateAddressRequest
import model.Address

import zio.*
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

trait AddressEndpoints:
  private type EP[I, O] = Endpoint[Unit, I, Unit, O, Any]
  val createEndpoint: EP[CreateAddressRequest, Address] =
    endpoint
      .tag("address")
      .name("create")
      .description("create an address entry")
      .in("addresses")
      .post
      .in(jsonBody[CreateAddressRequest])
      .out(jsonBody[Address])
    
  val getByIdEndpoint: EP[Long, Option[Address]] =
    endpoint
      .tag("address")
      .name("create")
      .description("get an address by id")
      .in("addresses" / path[Long]("id"))
      .get
      .out(jsonBody[Option[Address]])
    
  val getAllEndpoint: EP[Unit, List[Address]] =
    endpoint
      .tag("address")
      .name("getAll")
      .description("get all addresses")
      .in("addresses")
      .get
      .out(jsonBody[List[Address]])
  
  val deleteEndpoint: EP[Long, Address] =
    endpoint
      .tag("address")
      .name("delete")
      .description("delete an address")
      .in("addresses" / path[Long]("id"))
      .delete
      .out(jsonBody[Address])
