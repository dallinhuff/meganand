package com.dallinhuff.meganand.service

import com.dallinhuff.meganand.model.data.Address
import com.dallinhuff.meganand.model.request.CreateAddressRequest
import com.dallinhuff.meganand.repository.AddressRepository
import zio.*

trait AddressService:
  def create(req: CreateAddressRequest): Task[Address]
  def read(id: Long): Task[Option[Address]]
  def readAll: Task[List[Address]]
  def update(id: Long, fn: Address => Address): Task[Address]
  def delete(id: Long): Task[Address]

class AddressServiceLive private (repo: AddressRepository)
    extends AddressService:
  override def create(req: CreateAddressRequest): Task[Address] =
    repo.create(
      Address(
        -1L,
        req.firstName,
        req.lastName,
        req.streetAddress,
        req.apartment,
        req.city,
        req.state,
        req.zipCode,
        req.country
      )
    )

  override def read(id: Long): Task[Option[Address]] = repo.read(id)

  override def readAll: Task[List[Address]] = repo.readAll

  override def update(id: Long, fn: Address => Address): Task[Address] =
    repo.update(id, fn)

  override def delete(id: Long): Task[Address] = repo.delete(id)
end AddressServiceLive

object AddressServiceLive:
  val layer = ZLayer:
    ZIO.service[AddressRepository].map(AddressServiceLive(_))
